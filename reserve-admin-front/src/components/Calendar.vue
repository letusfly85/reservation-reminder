<template>
 <div v-if="dataReady" id="calendar" ref="calendar" style="width: 80%; margin-left: 10%; margin-top: 2rem;">
   <div ref="eventModal" class="modal fade" style="width: 1500px;">
     <div class="modal-dialog modal-lg">
       <div class="modal-content">
         <div class="modal-header">
           <div class="modal-body">
             <div class="form-group">
               <label for="eventTitle">予定名</label>
               <input type="text" class="form-control" id="eventTitle" v-model="form.title">
             </div>
             <div class="form-group">
               <label for="eventDescription">説明</label>
               <textarea class="form-control" id="eventDescription" v-model="form.description" />
             </div>
             <div class="form-check">
               <input class="form-check-input" type="checkbox" v-model="allDayCheckFlag" id="allDayCheck">
               <label class="form-check-label" for="allDayCheck">
                 終日
               </label>
             </div>
             <div v-show="!allDayCheckFlag">
               <div class="col-4 pt-2">
                 <flat-pickr :config="configs.timePicker"
                             class="form-control float-left"
                             v-model="pickerData.start"
                             placeholder="開始時間">
                 </flat-pickr>
               </div>
               <div class="col-4 pl-3 float-left">
                 <flat-pickr :config="configs.timePicker"
                             class="form-control float-left"
                             v-model="pickerData.end"
                             placeholder="終了時間">
                 </flat-pickr>
               </div>
             </div>
           </div>
         </div>
         <div class="modal-footer">
           <button type="button" class="btn btn-primary" v-on:click="updateCalenderEvent">Save changes</button>
           <button type="button" class="btn btn-danger" v-on:click="destroyCalenderEvent">Destroy</button>
           <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
         </div>
       </div>
     </div>
   </div>
   <!--
    https://fullcalendar.io/docs/bootstrapFontAwesome
    https://fontawesome.com/start
   -->
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
 </div>
</template>

<script>
import moment from 'moment'
import $ from 'jquery'
import 'fullcalendar/dist/fullcalendar'
import 'fullcalendar/dist/fullcalendar.css'
import 'bootswatch/dist/flatly/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import flatPickr from 'vue-flatpickr-component'
import 'flatpickr/dist/flatpickr.css'

import ReservationService from './service/ReservationService'

export default {
  name: 'Calendar',
  components: { flatPickr },
  data () {
    return {
      dataReady: false,
      element: undefined,
      events: [],
      form: {
        title: '',
        description: ''
      },
      pickerData: {
        start: '',
        end: ''
      },
      allDayCheckFlag: true,
      eventClickedFlag: false,
      targetEvent: {},
      configs: {
        timePicker: {
          wrap: true,
          enableTime: true,
          enableSeconds: true
        }
      }
    }
  },
  methods: {
    updateCalenderEvent: function () {
      let eventData = {}
      eventData.title = this.form.title
      eventData.start = this.pickerData.start
      eventData.end = this.pickerData.end
      eventData.allDay = this.allDayCheckFlag

      console.log(eventData)
      console.log(this.targetEvent._id)
      this.element.fullCalendar('renderEvent', eventData)
      this.element.fullCalendar('unselect')
      if (this.eventClickedFlag) {
        // FIXME updateEvents API not work
        // this.element.fullCalendar('updateEvents', [this.targetEvent])

        this.element.fullCalendar('removeEvents', [this.targetEvent._id])
        let params = {
          id: this.targetEvent.eventId,
          name: eventData.title,
          reserved_user_id: 'FIXME',
          all_day_flag: eventData.allDay,
          description: this.form.description,
          reserved_from: moment(eventData.start).format('YYYY-MM-DDThh:mm:ss'),
          reserved_to: moment(eventData.end).format('YYYY-MM-DDThh:mm:ss')
        }
        ReservationService.updateReservation(params, (response) => {
          console.log(response)
        }, (error) => {
          console.error(error)
        })
      } else {
        let params = {
          id: 1,
          name: eventData.title,
          reserved_user_id: 'FIXME',
          all_day_flag: eventData.allDay,
          description: this.form.description,
          reserved_from: moment(eventData.start).format('YYYY-MM-DDThh:mm:ss'),
          reserved_to: moment(eventData.end).format('YYYY-MM-DDThh:mm:ss')
        }
        ReservationService.createReservation(params, (response) => {
          console.log(response)
        }, (error) => {
          console.error(error)
        })
      }

      this.targetEvent = {}
      this.form = {}
      this.allDayCheckFlag = true

      const modalDom = $(this.$refs.eventModal)
      modalDom.modal('hide')
    },
    destroyCalenderEvent: function () {
      let eventId = this.targetEvent.eventId
      ReservationService.removeReservation(eventId, async (response) => {
        console.log(response)
        this.element.fullCalendar('removeEvents', [this.targetEvent._id])
        const modalDom = $(this.$refs.eventModal)
        modalDom.modal('hide')
      }, (error) => {
        console.error(error)
      })
    },
    onSelectFunction: function (start, end) {
      console.log(start)
      console.log(end)
      this.allDayCheckFlag = !start.hasTime() && !end.hasTime()

      // FIXME apply business hour
      this.pickerData.start = moment(start).format('YYYY-MM-DD HH:mm:ss')
      this.pickerData.end = moment(end).format('YYYY-MM-DD HH:mm:ss')

      this.form = {
        title: '',
        description: ''
      }

      const modalDom = $(this.$refs.eventModal)
      modalDom.modal('show')
    },
    onDragStartFunction: function (event) {
      console.log('starting')
      console.log(event)
    },
    onDragStopFunction: function (event) {
      console.log('stopping')
      console.log(event)
      this.element.fullCalendar('unselect')
    },
    onReSizeFunction: function (event) {
      console.log('resizing')
      console.log(event)
    },
    onDropFunction: function (event) {
      console.log('dropping')
      console.log(event)
      this.element.fullCalendar('unselect')
    },
    eventClickHandler: function (event) {
      this.allDayCheckFlag = event.allDay
      this.pickerData.start = moment(event.start).format('YYYY-MM-DD HH:mm:ss')
      this.pickerData.end = moment(event.end).format('YYYY-MM-DD HH:mm:ss')
      this.form.title = event.title
      this.form.description = event.description

      const modalDom = $(this.$refs.eventModal)
      this.eventClickedFlag = true
      this.targetEvent = event
      console.log(this.targetEvent)
      modalDom.modal('show')
    },
    loadEvents: function () {
      const userId = 'FIXME'
      const self = this
      ReservationService.findReservations(userId, (response) => {
        self.events = response.data.map(function (record) {
          let event = {
            eventId: record.id,
            title: record.name,
            description: record.description,
            allDay: record.all_day_flag,
            start: moment(record.reserved_from).format('YYYY-MM-DD HH:mm:ss'),
            end: moment(record.reserved_to).format('YYYY-MM-DD HH:mm:ss')
          }
          return event
        })
        this.dataReady = true
        this.renderFullCalendar()
      }, (error) => {
        console.error(error)
      })
    },
    renderFullCalendar: function () {
      this.element = $(this.$refs.calendar)
      const calendarOptions = {
        themeSystem: 'bootstrap4',
        header: {
          left: 'prev next today',
          center: 'title',
          right: 'month agendaWeek agendaDay listWeek'
        },
        // https://stackoverflow.com/questions/26458108/fullcalendar-v2-how-to-maintain-the-same-scroll-time-when-navigating-weeks
        // height: 'auto',
        timezone: 'local',
        scrollTime: '09:00:00',
        allDayText: '終日',
        defaultView: 'month',
        slotDuration: moment.duration(15, 'minutes'),
        slotLabelFormat: 'HH:mm',
        businessHours: true,
        eventLimit: true,
        buttonIcons: {
          close: 'fa-times',
          prev: 'fa-chevron-left',
          next: 'fa-chevron-right',
          prevYear: 'fa-angle-double-left',
          nextYear: 'fa-angle-double-right'
        },
        buttonText: {
          day: '今日',
          week: '今週',
          month: '今月'
        },
        monthNames: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        dayNames: ['日曜日', '月曜日', '火曜日', '水曜日', '木曜日', '金曜日', '土曜日'],
        dayNamesShort: ['日', '月', '火', '水', '木', '金', '土'],
        aspectRatio: 2,
        timeFormat: 'HH:mm',
        views: {
          month: {
            columnFormat: 'ddd'
          },
          week: {
            columnFormat: 'D[(]ddd[)]'
          },
          day: {
            columnFormat: 'D[(]ddd[)]'
          }
        },
        navLinks: true,
        selectable: true,
        selectHelper: true,
        select: this.onSelectFunction,
        eventDragStart: this.onDragStartFunction,
        eventDragStop: this.onDragStopFunction,
        eventDrop: this.onDropFunction,
        eventResize: this.onReSizeFunction,
        eventRender: this.addEventRender,
        updateRenderer: this.onUpdateEventData,
        eventClick: this.eventClickHandler,
        editable: true,
        droppable: true,
        events: this.events
      }
      this.element.fullCalendar(calendarOptions)
    }
  },
  created: async function () {
    await this.loadEvents()
  },
  async mounted () {
    await this.loadEvents()
  },
  onUpdateEventData: function (eventsData) {
    this.element.fullCalendar('removeEvents')
    this.element.fullCalendar('renderEvents', eventsData, true)
    this.element.fullCalendar('refetchEvents')
  },
  addEventRender: function (event, element) {
    element.bind('dblclick', () => {
      alert('onDoubleClickEvent')
      console.log(event)
    })
  }
}
</script>

<style scoped>
  html, body {
    font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
  }
</style>

<template>
 <div id="calendar" ref="calendar" style="width: 80%; margin-left: 10%; margin-top: 2rem;">
   <!--
    https://fullcalendar.io/docs/bootstrapFontAwesome
    https://fontawesome.com/start
   -->
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" integrity="sha384-gfdkjb5BdAXd+lj+gudLWI+BXq4IuLW5IT+brZEZsLFm++aCMlF1V92rMkPaX4PP" crossorigin="anonymous">
 </div>
</template>

<script>
import moment from 'moment'
import 'fullcalendar/dist/fullcalendar'
import 'fullcalendar/dist/fullcalendar.css'
import 'bootswatch/dist/flatly/bootstrap.css'
import $ from 'jquery'

export default {
  name: 'Calendar',
  data () {
    return {
      element: {},
      events: []
    }
  },
  methods: {
    onSelectFunction: function (start, end) {
      console.log(start, end)
      const eventTitle = prompt('予定を追加')
      if (eventTitle) {
        const eventData = {
          start: start,
          end: end,
          title: eventTitle
        }
        console.log(eventData)
        this.element.fullCalendar('renderEvent', eventData)
      }
      this.element.fullCalendar('unselect')
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
    }
  },
  created: function () {
  },
  mounted () {
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
        // prev: '前へ',
        // next: '次へ'
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
      editable: true,
      droppable: true,
      events: this.events
    }
    this.element.fullCalendar(calendarOptions)
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

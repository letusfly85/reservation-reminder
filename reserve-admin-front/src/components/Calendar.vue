<template>
 <div id="calendar" ref="calendar"></div>
</template>

<script>
import moment from 'moment'
import 'fullcalendar'
import 'fullcalendar/dist/fullcalendar.css'
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
      const eventTitle = prompt('何の日？')
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
      header: {
        left: 'prev next today',
        center: 'title',
        right: 'month agendaWeek agendaDay'
      },
      height: 'auto',
      allDayText: '終日',
      defaultView: 'month',
      slotDuration: moment.duration(15, 'minutes'),
      slotLabelFormat: 'HH:mm',
      businessHours: true,
      eventLimit: true,
      buttonText: {
        day: '今日',
        week: '今週',
        month: '今月'
      },
      monthNames: ['１月', '２月', '３月', '４月', '５月', '６月', '７月', '８月', '９月', '１０月', '１１月', '１２月'],
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

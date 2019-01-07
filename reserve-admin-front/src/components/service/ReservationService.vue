<script>
import ApiClient from './ApiClient'

const pathPrefix = '/api/v1/reserves'
export default {
  name: 'ReservationService',
  findReservations: function (userId, callback, errorHandler) {
    let path = `${pathPrefix}?userId=${userId}`
    ApiClient.search(path, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  createReservation: function (userId, params, callback, errorHandler) {
    let path = `${pathPrefix}?userId=${userId}`
    ApiClient.create(path, params, (response) => {
      console.log(response)
    }, (error) => {
      console.error(error)
    })
  },
  updateReservation: function () {},
  removeReservation: function (eventId, callback, errorHandler) {
    let path = `${pathPrefix}`
    ApiClient.destroy(path, eventId, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  }
}
</script>

<script>
import ApiClient from './ApiClient'

const pathPrefix = '/api/v1/reserves'
export default {
  name: 'ReservationService',
  findReservations: function (userId, callback, errorHandler) {
    let path = `${pathPrefix}?user_id=${userId}`
    ApiClient.search(path, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  createReservation: function (params, callback, errorHandler) {
    let path = `${pathPrefix}`
    ApiClient.create(path, params, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  updateReservation: function (params, callback, errorHandler) {
    let path = `${pathPrefix}`
    ApiClient.update(path, params, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
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

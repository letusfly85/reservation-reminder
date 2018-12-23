<script>
import ApiClient from './ApiClient'

export default {
  name: 'ReservationService',
  createReservation: function (userId, params) {
    let path = `/api/v1/reserves?userId=${userId}`
    ApiClient.create(path, params, (response) => {
      console.log(response)
    }, (error) => {
      console.error(error)
    })
  },
  updateReservation: function () {},
  removeReservation: function () {}
}
</script>

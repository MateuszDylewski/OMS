<template>
  <div class="myTable">
    <div class="tableHeader d-flex">
      <h4 id="title">Moje rezerwacje</h4>
    </div>
    <div class="table-responsive">
      <table class="table table-hover table-sm" style="padding: 15px">
        <thead class="thead-light">
        <tr>
          <th class="text-center">Nazwa</th>
          <th class="text-center">Piętro</th>
          <th style="width: 50%" class="text-center">Opis</th>
          <th class="text-center">Rozmiar</th>
          <th class="text-center">Miejsca</th>
          <th class="text-center">Początek</th>
          <th class="text-center">Koniec</th>
          <th class="text-center">Akcje</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="reservation in this.reservations"
            :key="reservation.reservationId"
            :class="reservation.space.blocked ? 'table-danger' : 'table-light'">
          <td class="align-middle text-center">{{ reservation.space.name }}</td>
          <td class="align-middle text-center">{{ reservation.space.floor }}</td>
          <td class="align-middle text-center">{{ reservation.space.description }}</td>
          <td class="align-middle text-center" v-html="translateSize(reservation.space.size)"></td>
          <td class="align-middle text-center">{{ reservation.space.allowedAttendees }}</td>
          <td class="align-middle text-center">{{ reservation.startDate }}</td>
          <td class="align-middle text-center">{{ reservation.endDate }}</td>
          <td class="align-middle">
            <div class="d-flex">
              <button class="actions btn btn-danger btn-sm btn-rounded ml-2 ml-auto"
                      @click="cancleReservation(reservation.reservationId)">
                Anuluj
              </button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex"
import { getCookie } from "../../services/authentication/userAuthenticationService";

export default {
  name: 'MyReservations',
  components: {

  },
  data() {
    return {
      reservations: [],
    }
  },
  mounted() {
    fetch(`/api/reservation/usersReservations/${sessionStorage.getItem('userId')}`, {
      headers: {
        'Authorization': 'Bearer ' + getCookie('accessToken')
      }
    })
      .then(response => response.json())
      .then(data => {
        this.reservations = data.map(reservation => ({
          reservationId: reservation.reservationId,
          space: {
            spaceId: reservation.space.spaceId,
            name: reservation.space.name,
            type: reservation.space.type,
            floor: reservation.space.floor.toString(),
            description: reservation.space.description,
            size: (reservation.space.size == null ? (reservation.space.area == null ? '' : reservation.space.area + 'm2') : reservation.space.size.toString()),
            allowedAttendees: reservation.space.allowedAttendees == null ? '' : reservation.space.allowedAttendees.toString(),
            blocked: reservation.space.blocked
          },
          startDate: reservation.startDate,
          endDate: reservation.endDate
        }))
      });
  },
  methods: {
    translateSize: function (size) {
      if(size === "LARGE"){
        return "Duże";
      } else if(size ==="REGULAR") {
        return "Średnie";
      } else if(size === "SMALL") {
        return "Małe";
      }
      return size;
    },
    cancleReservation: async function (reservationId) {
      const requestOptions = {
        method: "DELETE",
        headers: {
          'Authorization': 'Bearer ' + getCookie('accessToken')
        }
      };
      await fetch(`/api/reservation/${reservationId}`, requestOptions);
      window.location.reload();
    }
  },
  computed: {

  }
}
</script>

<style scoped>
/*div {
  border: red 1px solid;
}*/
.table th:first-child,
.table td:first-child {
  position: sticky;
  left: 0;
}

.myTable {
  border: #eaeaea 2px solid;
  border-radius: 10px;
}

.actions {
  margin: 2px;
}

.tableHeader {
  padding: 8px 8px 0 8px;
  height: 45px;
}
</style>
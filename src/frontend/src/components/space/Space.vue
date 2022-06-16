<template>
  <div class="myTable">
    <div class="tableHeader d-flex">
      <h4 id="title">Miejsca</h4>
      <div class="search input-group input-group-sm">
        <div class="input-group-prepend">
          <span class="input-group-text">
            <fa icon="search" size="sm"/>
          </span>
        </div>
        <input type="text" v-model="searchInput" class="form-control" aria-label="Small" >
      </div>
      <button class="btn btn-link ml-auto btn-sm" @click="showFilters = !showFilters">Filtry</button>
      <button class="addSpaceButton btn btn-link btn-sm"
              @click="showAddSpaceModal = true"
              v-if="user.userRole === 'ADMIN' || user.userRole === 'HRUSER'">
        Dodaj miejsce
      </button>
      <Teleport to="body">
        <AddSpaceModal :show="showAddSpaceModal" @close="showAddSpaceModal = false">
          <template #header>
            <h5>Nowe miejsce</h5>
          </template>
        </AddSpaceModal>
      </Teleport>
    </div>
    <div class="filters d-flex flex-wrap" v-if="showFilters">
      <div class="filter input-group input-group-sm pr-1">
        <div class="input-group-prepend">
          <span class="input-group-text">Nazwa</span>
        </div>
        <input type="text" v-model="nameFilter" class="form-control">
      </div>
      <div class="filter input-group input-group-sm pr-1">
        <div class="input-group-prepend">
          <span class="input-group-text">Piętro</span>
        </div>
        <input type="text" v-model="floorFilter" class="form-control">
      </div>
      <div class="filter input-group input-group-sm pr-1">
        <div class="input-group-prepend">
          <span class="input-group-text">Opis</span>
        </div>
        <input type="text" v-model="descriptionFilter" class="form-control">
      </div>
      <div class="ml-auto mr-auto pt-2">
        <button type="button" class="close" @click="clearFilters" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
    </div>
    <div class="table-responsive">
      <table class="table table-hover table-sm" style="padding: 15px">
        <thead class="thead-light">
          <tr>
            <th class="text-center">Nazwa</th>
            <th class="text-center">Piętro</th>
            <th class="text-center" style="width: 50%">Opis</th>
            <th class="text-center">Rozmiar</th>
            <th class="text-center">Miejsca</th>
            <th class="text-center">Akcje</th>
          </tr>
        </thead>
        <tbody>
          <Teleport to="body">
            <ReservationModal :show="showReservationModal" :space="actionSpace" @close="showReservationModal = false">
              <template #header>
                <h5>Rezerwacja miejsca</h5>
              </template>
            </ReservationModal>
          </Teleport>
          <Teleport to="body">
            <EditSpaceModal :show="showEditSpaceModal" :space="actionSpace" @close="showEditSpaceModal = false">
              <template #header>
                <h5>Edycja miejsca</h5>
              </template>
            </EditSpaceModal>
          </Teleport>
          <tr v-for="space in filterTable" :key="space.spaceId" :class="space.blocked ? 'table-danger' : 'table-light'">
            <td class="align-middle text-center" v-html="highlightMatches(space.name)"></td>
            <td class="align-middle text-center" v-html="highlightMatches(space.floor)"></td>
            <td class="align-middle text-center" v-html="highlightMatches(space.description)"></td>
            <td class="align-middle text-center" v-html="highlightMatches(translateSize(space.size))"></td>
            <td class="align-middle text-center" v-html="highlightMatches(space.allowedAttendees)"></td>
            <td class="align-middle">
              <div class="d-flex">
                <button class="actions btn btn-primary btn-sm btn-rounded ml-auto"
                        :disabled="space.blocked"
                        @click="showReservationModal = true; actionSpace = space">
                  Rezerwuj
                </button>
                <button class="actions btn btn-warning btn-sm btn-rounded ml-2"
                        @click="changeBlockedStatus(space.spaceId)"
                        v-if="!space.blocked && ( user.userRole === 'ADMIN' || user.userRole === 'HRUSER')">
                  Blokuj
                </button>
                <button class="actions btn btn-warning btn-sm btn-rounded ml-2"
                        @click="changeBlockedStatus(space.spaceId)"
                        v-else-if="user.userRole === 'ADMIN' || user.userRole === 'HRUSER'">
                  Odblokuj
                </button>
                <button class="actions btn btn-info btn-sm btn-rounded ml-2"
                        @click="showEditSpaceModal = true; actionSpace = space"
                        v-if="user.userRole === 'ADMIN' || user.userRole === 'HRUSER'">
                  Edytuj
                </button>
                <button class="actions btn btn-danger btn-sm btn-rounded ml-2"
                        @click="deleteSpace(space.spaceId, space.type)"
                        v-if="user.userRole === 'ADMIN'">
                  Usuń
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
import AddSpaceModal from "@/components/space/AddSpaceModal";
import ReservationModal from "@/components/space/ReservationModal";
import EditSpaceModal from "@/components/space/EditSpaceModal";
import { getCookie } from "../../services/authentication/userAuthenticationService";
import { mapGetters } from 'vuex';

export default {
  name: 'SpaceTable',
  components: {
    AddSpaceModal,
    ReservationModal,
    EditSpaceModal
  },
  data() {
    return {
      spaces: [],
      actionSpace: {},

      nameFilter: '',
      floorFilter: '',
      descriptionFilter: '',
      searchInput: '',

      showAddSpaceModal: false,
      showReservationModal: false,
      showEditSpaceModal: false,

      showFilters: false,
      showSearch: false,

      //userRole: '',
    }
  },
  mounted() {
    fetch(`/api/spaces/`, {
      headers: {
        'Authorization': 'Bearer ' + getCookie('accessToken')
      }
    })
      .then(response => response.json())
      .then(data => {
        this.spaces = data.map(space => ({
          spaceId: space.spaceId,
          name: space.name,
          type: space.type,
          floor: space.floor.toString(),
          description: space.description,
          size: (space.size == null ? (space.area == null ? '' : space.area + 'm2') : space.size.toString()),
          allowedAttendees: space.allowedAttendees == null ? '' : space.allowedAttendees.toString(),
          blocked: space.blocked
        }))
      });
  },
  methods: {
    highlightMatches: function (text) {
      if(text === null) return text;
      const matchExists = text.toLowerCase().includes(this.searchInput.toLowerCase());
      if (!matchExists || this.searchInput === '') return text;

      const re = new RegExp(this.searchInput, 'ig');
      return text.replace(re, matchedText => `<strong style="color: #8FBC8F; font-size: large">${matchedText}</strong>`);
    },
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
    clearFilters: function () {
      this.nameFilter = '';
      this.floorFilter = '';
      this.descriptionFilter = '';
    },
    changeBlockedStatus: async function (spaceId) {
      const requestOptions = {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          'Authorization': 'Bearer ' + getCookie('accessToken')
        }
      };
      await fetch(`/api/spaces/changeBlockStatus/${spaceId}`, requestOptions)
        .then(response => response.json());
        window.location.reload();
    },
    deleteSpace: async function (spaceId, type) {
      const requestOptions = {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          'Authorization': 'Bearer ' + getCookie('accessToken')
        }
      };
      await fetch(`/api/spaces/deleteSpace/${spaceId}/${type}`, requestOptions)
          .then(response => response.json());
      window.location.reload();
    }
  },
  computed: {
    ...mapGetters(['user']),
    filterTable() {
      return this.spaces.filter(row => {
        const name = row.name.toString().toLowerCase();
        const floor = row.floor.toString().toLowerCase();
        const description = row.description.toString().toLowerCase();
        const size = row.size.toString().toLowerCase();
        const allowedAttendees = row.allowedAttendees.toString().toLowerCase();

        const searchedName = this.nameFilter.toLowerCase();
        const searchedFloor = this.floorFilter.toLowerCase();
        const searchedDescription = this.descriptionFilter.toLowerCase();
        const search = this.searchInput.toLowerCase();

        return name.includes(searchedName)
            && floor.includes(searchedFloor)
            && description.includes(searchedDescription)
            && (
                name.includes(search)
                || floor.includes(search)
                || description.includes(search)
                || size.includes(search)
                || allowedAttendees.includes(search)
            );

      })
    }
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

.tableHeader {
  padding: 8px 8px 0 8px;
  height: 45px;
}

.search {
  min-width: 150px;
  width: 30%;
  height: 25px;
  margin-left: auto;
}

.filters {
  background: #f1f1f1;
  padding: 5px;
}

.filter {
  min-width: 200px;
  width: 32.5%;
  padding: 5px;
}

.actions {
  margin: 2px;
}

@media screen and (max-width: 1096px) {
  #title {
    display: none;
  }
  .search {
    margin-left: 0;
    width: 30%;
  }
}
</style>
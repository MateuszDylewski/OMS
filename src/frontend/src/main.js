import { createApp } from 'vue';
import App from './App.vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas }  from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from'@fortawesome/vue-fontawesome';
import router from "@/router";
import VCalendar from 'v-calendar';
import 'bootstrap';
import '../node_modules/bootstrap/scss/bootstrap.scss';
import { defineComponent } from "vue";
import Popper from "vue3-popper";
import Vuex from 'vuex';
import store from './vuex';

export default defineComponent({
    components: {
        Popper,
    },
});

library.add(fas);

createApp(App)
    .use(Vuex)
    .use(store)
    .use(router)
    .use(VCalendar, {})
    .component('fa', FontAwesomeIcon)
    .mount('#app');


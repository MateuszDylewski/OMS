import {createRouter, createWebHistory} from "vue-router";
import Users from "@/components/user/Users";
import Home from "@/components/Home";
import CalendarPage from '@/components/CalendarPage';
import SpacePage from '@/components/space/Space';
import MyReservations from "@/components/space/MyReservations";
import EventRoom from "../webRTC/EventRoom";


const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/users',
        name: 'Users',
        component: Users
    },
    {
        path: '/calendar',
        name: 'CalendarPage',
        component: CalendarPage
    },
    {
        path: '/space',
        name: 'Space',
        component: SpacePage
    },
    {
        path: '/myReservations',
        name: 'MyReservations',
        component: MyReservations
    },
    {
        path: '/eventRoom',
        name: EventRoom,
        component: EventRoom
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
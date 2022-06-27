import {createRouter, createWebHistory} from "vue-router";
import Users from "@/components/user/Users";
import Login from "@/components/Login";
import CalendarPage from '@/components/CalendarPage';
import SpacePage from '@/components/space/Space';
import MyReservations from "@/components/space/MyReservations";
import EventRoom from "../webRTC/EventRoom";
import Home from "../components/Home";


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
        path: '/calendar/eventRoom',
        name: 'EventRoom',
        component: EventRoom
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router;
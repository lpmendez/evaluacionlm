import Main from '../components/main/Main.vue';
import Login from '../components/signin/Login.vue';
import Error from '../components/error/Error.vue';

export const routes =[
    {path:'*', redirect: '/'},
    {
        path:'/', 
        name: 'main',
        component: Main,
        meta:{
            requiresAuth: true
        }},
    {path:'/login', name: 'login', component: Login},
    {path:'/error', name: 'error', component: Error}
];
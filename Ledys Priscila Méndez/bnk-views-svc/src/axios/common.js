import axios from 'axios';
import {store} from '../store/store';

export const http = axios.create({
    baseURL: `http://192.168.1.30:1999/`,
    headers: {
      Authorization: store.state.token
    }
})
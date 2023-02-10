import axios from 'axios';
import { paths } from '../constants/constants';

export const axiosPrivate = axios.create({
    baseURL: paths.BASE_URL,
    headers: {
        'Content-type': 'application/json'
    },
    withCredentials: true
})

export default axios.create({
    baseURL: paths.BASE_URL
})
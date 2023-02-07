import axios from 'axios';
import { paths } from '../constants/constants';

export const axiosPrivate = axios.create({
    baseURL: paths.LOCAL_BASE_URL,
    headers: {
        'Content-type': 'applications/json'
    },
    withCredentials: true
})

export default axios.create({
    baseURL: paths.LOCAL_BASE_URL
})
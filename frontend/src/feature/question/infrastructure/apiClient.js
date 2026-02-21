import axios from "axios";
import {config} from "./config.js";

const apiClient = axios.create({
    baseURL: config.apiUrl,
    timeout: config.timeout
});

export default apiClient;
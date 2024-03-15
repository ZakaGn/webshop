import axios from 'axios'
import auth from "./auth";

const axiosInstance = axios.create({baseURL: 'http://localhost:8080'})

axiosInstance.interceptors.request.use(
	config => {
		const token = auth.getToken()
		if(token){config.headers.Authorization = token}
		return config
	},
	error => Promise.reject(error)
)

export default axiosInstance

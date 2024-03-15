import axios from 'axios'
import auth from './auth'

const baseURL = 'http://localhost:8080'

const axiosInstance = axios.create({
	baseURL,
})

axiosInstance.interceptors.request.use(
	config => {
		const token = auth.getToken()
		if(token){config.headers['Authorization'] = token}
		return config
	},
	error => Promise.reject(error)
)

export const api = {
	login: async(email, password) => {
		try{
			const response = await axiosInstance.post('/user/login', {email, password})
			const {token} = response.data
			auth.saveToken(token)
			return response.data
		}catch(error){
			throw error
		}
	},

	register: async(userData) => {
		try{
			const response = await axiosInstance.post('/user/register', userData)
			return response.data
		}catch(error){
			throw error
		}
	},

	fetchUserData: async() => {
		try{
			const response = await axiosInstance.get('/user/data')
			return response.data
		}catch(error){
			throw error
		}
	}
}

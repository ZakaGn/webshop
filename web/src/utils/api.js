import auth from './auth'
import axiosInstance from "./axiosInstance";

export const api = {
	login: async(email, password) => {
		try{
			const response = await axiosInstance.post('/user/login', {email, password})
			const {token} = response.data
			auth.saveToken(token)
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
	},

	updateUserProfile: async (userData) => {
		return axiosInstance.post('/user/update', userData)
	}

}

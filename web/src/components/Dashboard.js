import React, {useEffect, useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import {api} from '../utils/api'
import auth from '../utils/auth'

const Dashboard = () => {
	const [userInfo, setUserInfo] = useState(null)
	const [isLoading, setIsLoading] = useState(true)
	const navigate = useNavigate()

	useEffect(() => {
		const fetchUserInfo = async() => {
			if(!auth.isAuthenticated()){
				toast.error('You must be logged in to view this page')
				navigate('/login')
				return
			}
			try{
				const data = await api.fetchUserData()
				setUserInfo(data)
			}catch(error){
				toast.error('Error fetching user information')
				//auth.logout()
			}finally{
				setIsLoading(false)
			}
		}
		fetchUserInfo()
	}, [navigate])

	if(isLoading){return <div>Loading...</div>}
	if(!userInfo){return <div>No user information available.</div>}
	return (
		<div className="dashboard-container">
			<h1>Welcome back, {userInfo.firstName}!</h1>
			<p>This is your personal dashboard where you can manage your account, view orders, and more.</p>
		</div>
	)
}

export default Dashboard

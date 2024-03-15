import React, {useEffect, useState} from 'react'
import {toast} from 'react-toastify'
import {api} from '../utils/api'
import './Dashboard.css'
import {useNavigate} from "react-router-dom";

const Dashboard = () => {
	const [userInfo, setUserInfo] = useState(null)
	const navigate = useNavigate()

	useEffect(() => {
		const fetchUserInfo = async() => {
			try{
				const data = await api.fetchUserData()
				setUserInfo(data)
			}catch(error){
				toast.error('Error fetching user information')
			}
		}

		fetchUserInfo()
	}, [])

	const handleEditProfileClick = () => {
		navigate('/edit-profile')
	}

	if(!userInfo){
		return <div className="dashboard-loading">Loading...</div>
	}

	return (
		<div className="dashboard-container">
			<h1>Welcome back, {userInfo.firstName}!</h1>
			<div className="user-info">
				<p><strong>First Name:</strong> {userInfo.firstName}</p>
				<p><strong>Last Name:</strong> {userInfo.lastName}</p>
				<p><strong>Email:</strong> {userInfo.email}</p>
			</div>
			<p>This is your personal dashboard where you can manage your account, view orders, and more.</p>
			<button onClick={handleEditProfileClick}>Edit Profile</button>
		</div>
	)
}

export default Dashboard

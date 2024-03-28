import './Dashboard.css'
import React, {useEffect, useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import {userService} from 'services/UserService'

const Dashboard = () => {
	const [userInfo, setUserInfo] = useState(null)
	const [loading, setLoading] = useState(true)
	const [error, setError] = useState(null)
	const navigate = useNavigate()
	let isMounted = true

	useEffect(() => {
		if(!isMounted) return
		fetchUser()
		return () => {isMounted = false}
	}, [])

	const fetchUser = () => {
		userService.fetchUser()
			.then(data => {
				setUserInfo(data)
				setLoading(false)
			})
			.catch(error => {
				const errorMessage = error.response?.data?.message || 'Error fetching user information'
				userService.logout()
				toast.error(errorMessage)
				setError(errorMessage)
				setLoading(false)
			})
	}

	const handleEditProfileClick = () => {
		navigate('/edit-profile')
	}

	if(loading){
		return <div className="dashboard-loading">Loading...</div>
	}

	if(error){
		return <div className="dashboard-loading">{error}</div>
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
			<button type="button" className="edit-profile" onClick={handleEditProfileClick}>Edit Profile</button>
		</div>
	)
}

export default Dashboard

import './Dashboard.css'
import React from 'react'
import {useNavigate} from 'react-router-dom'

const Dashboard = ({user}) => {
	const navigate = useNavigate()

	const handleEditProfileClick = () => {
		navigate('/edit-profile')
	}

	return (
		<div id={"dashboard"} className="dashboard-container">
			<h1>Welcome back, {user.firstName}!</h1>
			<div className="user-info">
				<p><strong>First Name:</strong> {user.firstName}</p>
				<p><strong>Last Name:</strong> {user.lastName}</p>
				<p><strong>Email:</strong> {user.email}</p>
			</div>
			<p>This is your personal dashboard where you can manage your account, view orders, and more.</p>
			<button type="button" className="edit-profile" onClick={handleEditProfileClick}>Edit Profile</button>
		</div>
	)
}

export default Dashboard

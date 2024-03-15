import React, {useEffect, useState} from 'react';
import axiosInstance from '../utils/axiosInstance';
import {toast} from 'react-toastify';

const Dashboard = () => {
	const [userInfo, setUserInfo] = useState(null);

	useEffect(() => {
		const fetchUserInfo = async() => {
			try{
				const response = await axiosInstance.get('/user/me');
				setUserInfo(response.data);
			}catch(error){toast.error('Error fetching user information')}
		};

		fetchUserInfo()
	}, [])

	if(!userInfo){return <div>Loading...</div>}

	return (
		<div className="dashboard-container">
			<h1>Welcome back, {userInfo.firstName}!</h1>
			<p>This is your personal dashboard where you can manage your account, view orders, and more.</p>
		</div>
	)
}

export default Dashboard;

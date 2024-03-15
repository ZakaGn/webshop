import './ProfileEdit.css'
import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom'
import {toast} from 'react-toastify'
import {api} from "../utils/api";

const ProfileEdit = () => {
	const [profile, setProfile] = useState({firstName: '', lastName: '', email: ''})
	const [loading, setLoading] = useState(false)
	const navigate = useNavigate()
	const [errors, setErrors] = useState({})

	useEffect(() => {
		const fetchProfile = async() => {
			setLoading(true)
			try{
				const data = await api.fetchUserData()
				setProfile(data)
			}catch(error){
				toast.error('Failed to fetch profile data')
			}finally{
				setLoading(false)
			}
		}
		fetchProfile()
	}, [])

	const handleChange = (event) => {
		const {name, value} = event.target
		setProfile((prevProfile) => ({...prevProfile, [name]: value}))
	}

	const validateForm = () => {
		let tempErrors = {}
		tempErrors.firstName = profile.firstName.length >= 3 && profile.firstName.length <= 50 ? "" : "First name must be between 3 and 50 characters"
		tempErrors.lastName = profile.lastName.length >= 3 && profile.lastName.length <= 50 ? "" : "Last name must be between 3 and 50 characters"
		tempErrors.email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(profile.email) && profile.email.length <= 100 ? "" : "Email should be valid and less than 100 characters"
		setErrors(tempErrors)
		return Object.values(tempErrors).every(x => x === "")
	}

	const handleSubmit = async(event) => {
		event.preventDefault();
		if (!validateForm()) {
			toast.error("Please correct the errors before submitting.");
			return;
		}

		setLoading(true);
		try {
			await api.updateUserProfile(profile);
			toast.success('Profile updated successfully');
			navigate('/dashboard');
		} catch(error) {
			toast.error('Failed to update profile');
		} finally {
			setLoading(false);
		}
	};


	if(loading){return <div>Loading...</div>}

	return (
		<div className="profile-edit-container">
			<h2>Edit Profile</h2>
			<form onSubmit={handleSubmit}>
				<div className="form-group">
					<label htmlFor="firstName">First Name</label>
					<input
						type="text"
						id="firstName"
						name="firstName"
						value={profile.firstName}
						onChange={handleChange}
						required
					/>
					{errors.firstName && <div className="error">{errors.firstName}</div>}
				</div>
				<div className="form-group">
					<label htmlFor="lastName">Last Name</label>
					<input
						type="text"
						id="lastName"
						name="lastName"
						value={profile.lastName}
						onChange={handleChange}
						required
					/>
					{errors.lastName && <div className="error">{errors.lastName}</div>}
				</div>
				<div className="form-group">
					<label htmlFor="email">Email</label>
					<input
						type="email"
						id="email"
						name="email"
						value={profile.email}
						onChange={handleChange}
						required
					/>
					{errors.email && <div className="error">{errors.email}</div>}
				</div>
				<button type="submit" disabled={loading}>
					Update Profile
				</button>
			</form>
		</div>
	)
}

export default ProfileEdit

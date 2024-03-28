import './ProfileEdit.css'
import React, {useState, useEffect} from 'react'
import {useNavigate} from 'react-router-dom'
import {toast} from 'react-toastify'
import {userService} from "services/UserService"

const ProfileEdit = () => {
	const [profile, setProfile] = useState({firstName: '', lastName: '', email: ''})
	const [loading, setLoading] = useState(false)
	const navigate = useNavigate()
	const [errors, setErrors] = useState({})

	useEffect(() => {
		setLoading(true)
		userService.fetchUser().then(response => {
			setProfile(response)
			setLoading(false)
		}).catch(error => {
			toast.error('Failed to fetch profile data')
			setLoading(false)
		})
	}, [])

	const handleChange = (event) => {
		const {name, value} = event.target
		setProfile(prev => ({...prev, [name]: value}))
	}

	const validateForm = () => {
		let isValid = true
		let tempErrors = {}

		if(!profile.firstName || profile.firstName.length < 3 || profile.firstName.length > 50){
			isValid = false
			tempErrors.firstName = 'First name must be between 3 and 50 characters'
		}

		if(!profile.lastName || profile.lastName.length < 3 || profile.lastName.length > 50){
			isValid = false
			tempErrors.lastName = 'Last name must be between 3 and 50 characters'
		}

		if(!profile.email || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(profile.email) || profile.email.length > 100){
			isValid = false
			tempErrors.email = 'Email should be valid and less than 100 characters'
		}

		setErrors(tempErrors)
		return isValid
	}

	const handleSubmit = async(event) => {
		event.preventDefault()
		if(!validateForm()){
			toast.error("Please correct the errors before submitting.")
			return
		}
		setLoading(true)
		try{
			await userService.updateUser(profile)
			toast.success('Profile updated successfully')
			navigate('/dashboard')
		}catch(error){
			const errorMessage = error.response?.data?.message || 'Failed to update profile'
			toast.error(errorMessage)
		}finally{
			setLoading(false)
		}
	}

	return (
		<div id={"profile-edit"} className="profile-edit-container">
			<h2>Edit Profile</h2>
			<form onSubmit={handleSubmit}>
				<div className="form-group">
					<label htmlFor="firstName">First Name</label>
					<input type="text" id="firstName" name="firstName" value={profile.firstName} onChange={handleChange}
					       required/>
					{errors.firstName && <div className="error">{errors.firstName}</div>}
				</div>
				<div className="form-group">
					<label htmlFor="lastName">Last Name</label>
					<input type="text" id="lastName" name="lastName" value={profile.lastName} onChange={handleChange} required/>
					{errors.lastName && <div className="error">{errors.lastName}</div>}
				</div>
				<div className="form-group">
					<label htmlFor="email">Email</label>
					<input type="email" id="email" name="email" value={profile.email} onChange={handleChange} required/>
					{errors.email && <div className="error">{errors.email}</div>}
				</div>
				<button type="submit" disabled={loading}>Update Profile</button>
				<button type="button" className={"btn-profile-edit-cancel"} onClick={() => navigate('/dashboard')}>Cancel</button>
			</form>
		</div>
	)
}

export default ProfileEdit

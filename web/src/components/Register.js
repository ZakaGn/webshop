import './Register.css'
import React, {useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import {api} from "../utils/api";

const Register = () => {
	const [formData, setFormData] = useState({firstName: '', lastName: '', email: '', password: ''})
	const navigate = useNavigate()

	const handleChange = (e) => {
		const {name, value} = e.target
		setFormData((prevFormData) => ({...prevFormData, [name]: value}))
	}

	const handleSubmit = async(e) => {
		e.preventDefault()
		try{
			await api.register(formData)
			toast.success('Registration successful! Please login.')
			navigate("/login")
		}catch(error){
			toast.error(error.response?.data?.message || 'Registration failed. Please try again.')
		}
	}

	return (
		<div className="register-container">
			<h2>Register</h2>
			<form onSubmit={handleSubmit}>
				<div className="form-group">
					<label htmlFor="firstName">First Name:</label>
					<input
						type="text"
						id="firstName"
						name="firstName"
						value={formData.firstName}
						onChange={handleChange}
						required
					/>
				</div>
				<div className="form-group">
					<label htmlFor="lastName">Last Name:</label>
					<input
						type="text"
						id="lastName"
						name="lastName"
						value={formData.lastName}
						onChange={handleChange}
						required
					/>
				</div>
				<div className="form-group">
					<label htmlFor="email">Email:</label>
					<input
						type="email"
						id="email"
						name="email"
						value={formData.email}
						onChange={handleChange}
						required
					/>
				</div>
				<div className="form-group">
					<label htmlFor="password">Password:</label>
					<input
						type="password"
						id="password"
						name="password"
						value={formData.password}
						onChange={handleChange}
						required
					/>
				</div>
				<button type="submit">Register</button>
			</form>
		</div>
	)
}

export default Register

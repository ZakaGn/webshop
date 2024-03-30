import 'components/user/Register.css'
import React, {useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import userService from "services/UserService";

const Register = () => {
	const [formData, setFormData] = useState({firstName: '', lastName: '', email: '', password: ''})
	const [formErrors, setFormErrors] = useState({})
	const navigate = useNavigate()

	const validateForm = () => {
		let errors = {}
		let formIsValid = true

		if(!formData.firstName){
			errors.firstName = "First name is required"
			formIsValid = false
		}else if(formData.firstName.length < 3){
			errors.firstName = "First name must be longer than 2 characters"
			formIsValid = false
		}

		if(!formData.lastName){
			errors.lastName = "Last name is required"
			formIsValid = false
		}else if(formData.lastName.length < 3){
			errors.lastName = "Last name must be longer than 2 characters"
			formIsValid = false
		}

		if(!formData.email){
			errors.email = "Email is required"
			formIsValid = false
		}else if(!/\S+@\S+\.\S+/.test(formData.email)){
			errors.email = "Email address is invalid"
			formIsValid = false
		}

		if(!formData.password){
			errors.password = "Password is required"
			formIsValid = false
		}else if(formData.password.length < 6){
			errors.password = "Password must be 6 characters or more"
			formIsValid = false
		}

		setFormErrors(errors)
		return formIsValid
	}

	const handleChange = (e) => {
		const {name, value} = e.target
		setFormData(prevFormData => ({...prevFormData, [name]: value}))
	}

	const handleSubmit = async(e) => {
		e.preventDefault()
		if(!validateForm()) return

		try{
			await userService.register(formData)
			toast.success('Registration successful! Please login.')
			navigate("/login")
		}catch(error){
			toast.error(error.response?.data?.message || 'Registration failed. Please try again.')
		}
	}

	return (
		<div id={"register"} className="register-container">
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
					{formErrors.firstName && <p className="error">{formErrors.firstName}</p>}
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
					{formErrors.lastName && <p className="error">{formErrors.lastName}</p>}
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
					{formErrors.email && <p className="error">{formErrors.email}</p>}
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
					{formErrors.password && <p className="error">{formErrors.password}</p>}
				</div>
				<button type="submit">Register</button>
			</form>
		</div>
	)
}

export default Register

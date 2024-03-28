import 'components/user/Login.css'
import React, {useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import {userService} from "services/UserService"

const Login = () => {
	const [formData, setFormData] = useState({email: '', password: ''})
	const [formErrors, setFormErrors] = useState({})
	const navigate = useNavigate()

	const handleChange = (e) => {
		const {name, value} = e.target
		setFormData(prevState => ({...prevState, [name]: value}))
		if(formErrors[name]){setFormErrors(prevErrors => ({...prevErrors, [name]: ''}))}
	}

	const handleSubmit = async(e) => {
		e.preventDefault()
		const {email, password} = formData

		if(!email){
			setFormErrors(prev => ({...prev, email: "Email is required"}))
			return
		}

		if(!password || password.length < 3){
			setFormErrors(prev => ({...prev, password: "Password must be longer than 2 characters"}))
			return
		}

		userService
			.login(email, password)
			.then(response => {
				toast.success('Login successful!')
				navigate("/dashboard")
			})
			.catch(error => {
				toast.error(error.response?.data?.message || 'Failed to log in. Please try again.')
			})
	}

	return (
		<div className="login-container">
			<h2>Login</h2>
			<form onSubmit={handleSubmit}>
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
					{formErrors.email && <div className="error">{formErrors.email}</div>}
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
					{formErrors.password && <div className="error">{formErrors.password}</div>}
				</div>
				<button type="submit">Login</button>
			</form>
		</div>
	)
}

export default Login

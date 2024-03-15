import './Login.css'
import React, {useState} from 'react'
import {toast} from 'react-toastify'
import {useNavigate} from 'react-router-dom'
import {api} from "../utils/api"
import auth from '../utils/auth'

const Login = () => {
	const [formData, setFormData] = useState({email: '', password: ''})
	const [formErrors, setFormErrors] = useState({})
	const navigate = useNavigate()

	const handleChange = (e) => {
		const {name, value} = e.target
		setFormData(prevState => ({...prevState, [name]: value}))
		if(formErrors[name]){
			setFormErrors(prevErrors => ({...prevErrors, [name]: ''}))
		}
	}

	const handleSubmit = async(e) => {
		e.preventDefault()
		let hasError = false
		let errors = {}

		if(!formData.email){
			errors.email = "Email is required"
			hasError = true
		}
		if(!formData.password){
			errors.password = "Password is required"
			hasError = true
		}else if(formData.password.length < 3){
			errors.password = "Password must be longer than 2 characters";
			hasError = true;
		}

		if(hasError){
			setFormErrors(errors)
			return
		}

		try{
			const data = await api.login(formData.email, formData.password)
			auth.saveToken(data.token)
			toast.success('Login successful!')
			navigate("/")
		}catch(error){
			toast.error(error.response?.data?.message || 'Failed to log in. Please try again.')
		}
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

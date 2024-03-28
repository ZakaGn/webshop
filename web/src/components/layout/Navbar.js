import './Navbar.css'
import React, {useEffect, useState} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import auth from "../../utils/auth"
import {userService} from "../../services/UserService"

const Navbar = () => {
	const navigate = useNavigate()
	const isAuthenticated = auth.isAuthenticated()
	const isEmployer = isAuthenticated && auth.getRole() === 'ROLE_EMPLOYER'
	const [email, setEmail] = useState('')

	useEffect(() => {
		if(isAuthenticated){
			let em = userService.getEmail()
			if(!em) em = 'Guest'
			setEmail(em)
		}
	}, [isAuthenticated])

	const handleLogout = () => {
		userService.logout()
		navigate('/login', {replace: true})
	}

	return (
		<nav id={"navbar"}>
			<ul>
				<li><Link to="/">Home</Link></li>
				{isAuthenticated && (
					<>
						<li><Link to="/dashboard">Dashboard</Link></li>
						{isEmployer && <li><Link to="/admin/categories">Manage Categories</Link></li>}
					</>
				)}
				<li style={{marginLeft: 'auto'}}>
					{isAuthenticated ? (
						<>
							<span>{email || 'Guest'}</span>
							<button onClick={handleLogout}>Logout</button>
						</>
					) : (
						<>
							<Link to="/login">Login</Link>
							<Link to="/register">Register</Link>
						</>
					)}
				</li>
			</ul>
		</nav>
	)
}

export default Navbar

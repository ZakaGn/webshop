import React from 'react'
import 'App.css'
import {ToastContainer} from "react-toastify"
import AppRouter from 'Router'

function App() {
	return (
		<div className="App">
			<AppRouter />
			<ToastContainer
				position="top-center"
				autoClose={5000}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick
				rtl={false}
				pauseOnFocusLoss
				pauseOnHover
				theme="colored"
			/>
		</div>
	)
}

export default App

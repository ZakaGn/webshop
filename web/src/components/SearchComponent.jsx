import React, {useState} from 'react'
import {debounce} from 'lodash'
import './SearchComponent.css'

const SearchComponent = ({onSearch}) => {
	const [searchTerm, setSearchTerm] = useState('')

	const debouncedSearch = debounce((searchTerm) => {
		onSearch(searchTerm, false)
	}, 300)

	const handleSearchChange = (event) => {
		const value = event.target.value
		setSearchTerm(value)
		if(value.trim() === ''){
			onSearch(value, false)
		}else{
			onSearch(value, true)
			debouncedSearch(value)
		}
	}

	const handleResetSearch = () => {
		setSearchTerm('')
		onSearch('', false)
	}

	return (
		<div className="search-component">
			<div className="search-input-container">
				<input
					type="text"
					placeholder="Search..."
					value={searchTerm}
					onChange={handleSearchChange}
					className="search-input"
				/>
				{searchTerm && (
					<button onClick={handleResetSearch} className="reset-search-button">&times;</button>
				)}
			</div>
		</div>
	)
}

export default SearchComponent

import React, { useState } from 'react';
import './SearchComponent.css'; // Adjust the path as needed

const SearchComponent = ({ onSearch }) => {
	const [searchTerm, setSearchTerm] = useState('');

	const handleSearchChange = (event) => {
		setSearchTerm(event.target.value);
	};

	const handleSubmit = (event) => {
		event.preventDefault(); // Prevent the form from refreshing the page
		onSearch(searchTerm); // Trigger the search action passed from the parent component
	};

	return (
		<form className="search-form" onSubmit={handleSubmit}>
			<input
				type="text"
				placeholder="Search..."
				value={searchTerm}
				onChange={handleSearchChange}
				className="search-input"
			/>
			<button type="submit" className="search-button">Search</button>
		</form>
	);
};

export default SearchComponent;

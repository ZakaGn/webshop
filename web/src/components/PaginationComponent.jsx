import React from 'react';
import './PaginationComponent.css'; // Make sure to style your pagination as needed

const PaginationComponent = ({ totalPages, currentPage, onPageChange }) => {
	// Generate page numbers
	const pages = Array.from({ length: totalPages }, (_, index) => index + 1);

	return (
		<div className="pagination-container">
			<button
				className="pagination-button"
				onClick={() => onPageChange(currentPage - 1)}
				disabled={currentPage === 1}
			>
				Previous
			</button>
			{pages.map(page => (
				<button
					key={page}
					className={`pagination-button ${page === currentPage ? 'active' : ''}`}
					onClick={() => onPageChange(page)}
				>
					{page}
				</button>
			))}
			<button
				className="pagination-button"
				onClick={() => onPageChange(currentPage + 1)}
				disabled={currentPage === totalPages}
			>
				Next
			</button>
		</div>
	);
};

export default PaginationComponent;

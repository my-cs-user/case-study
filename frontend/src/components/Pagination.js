import React from 'react';
import { Pagination } from 'react-bootstrap';

function CustomPagination({ totalRecords, pageSize, currentPage, onPageChange }) {
  const totalPages = Math.ceil(totalRecords / pageSize);

  const handlePageChange = (page) => {
    onPageChange(page);
  };

  return (
    <Pagination>
      <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
      <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />

      {[...Array(totalPages)].map((_, index) => (
        <Pagination.Item
          key={index}
          active={index === currentPage}
          onClick={() => handlePageChange(index)}
        >
          {index + 1}
        </Pagination.Item>
      ))}

      <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages - 1} />
      <Pagination.Last onClick={() => handlePageChange(totalPages - 1)} disabled={currentPage === totalPages - 1} />
    </Pagination>
  );
}

export default CustomPagination;

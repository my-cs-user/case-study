import React, { useState, useEffect } from 'react';
import { Table, Button, Pagination, Alert, Form } from 'react-bootstrap';
import api from './api';
import EntityModal from './EntityModal';
import { employeeFields } from './formFields';

function DepartmentsEmployees() {
  const [departments, setDepartments] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalRecords, setTotalRecords] = useState(0);
  const [pageSize, setPageSize] = useState(20); // Default page size
  const [message, setMessage] = useState(null);
  const [messageType, setMessageType] = useState('');
  const [searchText, setSearchText] = useState(''); // Search text state

  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      const response = await api.get('/api/departments');
      setDepartments(response.data);
    } catch (error) {
      displayMessage(`Failed to fetch departments: ${error.message}`, 'error');
    }
  };

  const fetchEmployees = async (departmentId, page = 0, size = pageSize, search = '') => {
    try {
      const response = await api.get(`/api/employees/departments/${departmentId}`, {
        params: { page, size, searchText: search }
      });
      setEmployees(response.data.content);
      setTotalRecords(response.data.totalElements);
    } catch (error) {
      displayMessage(`Failed to fetch employees: ${error.message}`, 'error');
    }
  };

  const handleDepartmentSelect = (event) => {
    const selectedId = event.target.value;
    const department = departments.find((d) => d.id === parseInt(selectedId));
    setSelectedDepartment(department);
    fetchEmployees(department.id);
  };

  const handleEmployeeSelect = (employee) => {
    setSelectedEmployee(employee);
    setShowModal(true);
  };

  const handleCreateEmployee = async (newEmployee) => {
    try {
      await api.post('/api/employees', newEmployee);
      fetchEmployees(selectedDepartment.id);
      displayMessage('Employee created successfully!', 'success');
    } catch (error) {
      displayMessage(`Failed to create employee: ${error.message}`, 'error');
    }
    setShowCreateModal(false);
  };

  const handleUpdateEmployee = async (updatedEmployee) => {
    try {
      await api.put(`/api/employees/${updatedEmployee.id}`, updatedEmployee);
      fetchEmployees(selectedDepartment.id);
      displayMessage('Employee updated successfully!', 'success');
    } catch (error) {
      displayMessage(`Failed to update employee: ${error.message}`, 'error');
    }
    setShowModal(false);
  };

  const handleDeleteEmployee = async () => {
    try {
      await api.delete(`/api/employees/${selectedEmployee.id}`);
      fetchEmployees(selectedDepartment.id);
      displayMessage('Employee deleted successfully!', 'success');
    } catch (error) {
      displayMessage(`Failed to delete employee: ${error.message}`, 'error');
    }
    setShowModal(false);
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
    fetchEmployees(selectedDepartment.id, pageNumber, pageSize, searchText);
  };

  const handlePageSizeChange = (event) => {
    const size = parseInt(event.target.value, 10);
    setPageSize(size);
    setCurrentPage(0);
    if (selectedDepartment) {
      fetchEmployees(selectedDepartment.id, 0, size, searchText);
    }
  };

  const handleSearchChange = (event) => {
    const { value } = event.target;
    setSearchText(value);
    if (selectedDepartment) {
      fetchEmployees(selectedDepartment.id, 0, pageSize, value);
    }
  };

  const displayMessage = (msg, type) => {
    setMessage(msg);
    setMessageType(type);
    if (type === 'error') {
      console.error(msg);
    }
    if (window.messageTimeout) {
      clearTimeout(window.messageTimeout);
    }
    window.messageTimeout = setTimeout(() => {
      setMessage(null);
      setMessageType('');
    }, 5000);
  };

  return (
      <div className="container">
        <h2>Departments and Employees</h2>

        {/* Message Alert */}
        {message && (
            <Alert variant={messageType === 'error' ? 'danger' : 'success'}>
              {message}
            </Alert>
        )}

        {/* Department Selection Dropdown */}
        <div className="mb-3">
          <label>Select Department: </label>
          <select
              className="form-select"
              onChange={handleDepartmentSelect}
              value={selectedDepartment ? selectedDepartment.id : ''}
          >
            <option value="" disabled>
              Select a department
            </option>
            {departments.map((department) => (
                <option key={department.id} value={department.id}>
                  {department.name}
                </option>
            ))}
          </select>
        </div>

        {/* Search Bar */}
        {selectedDepartment && (
            <Form.Group className="mb-3">
              <Form.Label>Search Employees:</Form.Label>
              <Form.Control
                  type="text"
                  placeholder="Search employees"
                  value={searchText}
                  onChange={handleSearchChange}
              />
            </Form.Group>
        )}

        {/* Create Employee Button */}
        {selectedDepartment && (
            <Button className="mb-3" variant="primary" onClick={() => setShowCreateModal(true)}>
              Add New Employee
            </Button>
        )}

        {/* Employee Table */}
        {employees.length > 0 && (
            <div>
              <Table bordered>
                <thead>
                <tr>
                  <th>Name</th>
                  <th>Surname</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Department</th>
                </tr>
                </thead>
                <tbody>
                {employees.map((employee) => (
                    <tr key={employee.id} onClick={() => handleEmployeeSelect(employee)}>
                      <td>{employee.name}</td>
                      <td>{employee.surname}</td>
                      <td>{employee.email}</td>
                      <td>{employee.phone}</td>
                      <td>{employee.department}</td>
                    </tr>
                ))}
                </tbody>
              </Table>

              {/* Pagination */}
              <Pagination>
                {Array.from({ length: Math.ceil(totalRecords / pageSize) }).map((_, index) => (
                    <Pagination.Item
                        key={index}
                        active={index === currentPage}
                        onClick={() => handlePageChange(index)}
                    >
                      {index + 1}
                    </Pagination.Item>
                ))}
              </Pagination>

              {/* Page Size Selection Dropdown */}
              <Form.Group className="mb-3">
                <Form.Label>Select Records per Page:</Form.Label>
                <Form.Select value={pageSize} onChange={handlePageSizeChange}>
                  <option value={20}>20</option>
                  <option value={50}>50</option>
                  <option value={100}>100</option>
                </Form.Select>
              </Form.Group>

              <p>Total Records: {totalRecords}</p>
            </div>
        )}

        {/* Employee Detail Modal */}
        {selectedEmployee && (
            <EntityModal
                show={showModal}
                onHide={() => setShowModal(false)}
                entity={selectedEmployee}
                fields={employeeFields}
                masterEntities={departments} // Department information
                onUpdate={handleUpdateEmployee}
                onDelete={handleDeleteEmployee}
            />
        )}

        {/* Create Employee Modal */}
        {showCreateModal && (
            <EntityModal
                show={showCreateModal}
                onHide={() => setShowCreateModal(false)}
                entity={{}}
                fields={employeeFields}
                masterEntities={departments} // Department information
                onCreate={handleCreateEmployee}
            />
        )}
      </div>
  );
}

export default DepartmentsEmployees;

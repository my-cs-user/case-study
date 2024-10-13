import React, { useState, useEffect } from 'react';
import {Table, Button, Pagination, Alert, FormControl, InputGroup, Form} from 'react-bootstrap';
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
  const [message, setMessage] = useState(null);
  const [messageType, setMessageType] = useState('');
  const [searchText, setSearchText] = useState(''); // New state for search text

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

  const fetchEmployees = async (departmentId, page = 0, searchText = '') => {
    try {
      const response = await api.get(`/api/employees/departments/${departmentId}`, {
        params: { page, size: 10, searchText: searchText.trim() }
      });
      setEmployees(response.data.content);
      setTotalRecords(response.data.totalElements);
    } catch (error) {
      displayMessage(`Failed to fetch employees: ${error.message}`, 'error');
    }
  };

  const handleDepartmentSelect = (event) => {
    const selectedId = event.target.value;
    const department = departments.find((dept) => dept.id === parseInt(selectedId));
    setSelectedDepartment(department);
    fetchEmployees(department.id, 0, searchText); // Fetch employees with search text when department changes
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
    fetchEmployees(selectedDepartment.id, pageNumber, searchText); // Pass search text when changing pages
  };

  const handleSearchInputChange = (e) => {
    const value = e.target.value;
    setSearchText(value);
    if (selectedDepartment) {
      fetchEmployees(selectedDepartment.id, 0, value); // Fetch employees with search text when user types
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

        {message && (
            <Alert variant={messageType === 'error' ? 'danger' : 'success'}>
              {message}
            </Alert>
        )}

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
                  onChange={handleSearchInputChange}
              />
            </Form.Group>
        )}

        {selectedDepartment && (
            <Button className="mb-3" variant="primary" onClick={() => setShowCreateModal(true)}>
              Add New Employee
            </Button>
        )}

        {employees.length > 0 && (
            <div>
              <Table bordered>
                <thead>
                <tr>
                  <th>Name</th>
                  <th>Surname</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Salary</th>
                  <th>Department</th>
                </tr>
                </thead>
                <tbody>
                {employees.map((employee) => {
                  const departmentName =
                      departments.find((dept) => dept.id === employee.department)?.name || 'N/A';
                  return (
                      <tr key={employee.id} onClick={() => handleEmployeeSelect(employee)}>
                        <td>{employee.name}</td>
                        <td>{employee.surname}</td>
                        <td>{employee.email}</td>
                        <td>{employee.phone}</td>
                        <td>{employee.salary}</td>
                        <td>{departmentName}</td>
                      </tr>
                  );
                })}
                </tbody>
              </Table>

              <Pagination>
                {Array.from({ length: Math.ceil(totalRecords / 10) }).map((_, index) => (
                    <Pagination.Item
                        key={index}
                        active={index === currentPage}
                        onClick={() => handlePageChange(index)}
                    >
                      {index + 1}
                    </Pagination.Item>
                ))}
              </Pagination>

              <p>Total Records: {totalRecords}</p>
            </div>
        )}

        {selectedEmployee && (
            <EntityModal
                show={showModal}
                onHide={() => setShowModal(false)}
                entity={selectedEmployee}
                fields={employeeFields}
                masterEntities={departments}
                onUpdate={handleUpdateEmployee}
                onDelete={handleDeleteEmployee}
            />
        )}

        {showCreateModal && (
            <EntityModal
                show={showCreateModal}
                onHide={() => setShowCreateModal(false)}
                entity={{}}
                fields={employeeFields}
                masterEntities={departments}
                onCreate={handleCreateEmployee}
            />
        )}
      </div>
  );
}

export default DepartmentsEmployees;

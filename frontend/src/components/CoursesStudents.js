import React, {useEffect, useState} from 'react';
import {Alert, Button, Form, Pagination, Table} from 'react-bootstrap';
import api from './common/api';
import EntityModal from './common/EntityModal';
import {studentFields} from './common/formFields';

function CoursesStudents() {
    const [courses, setCourses] = useState([]);
    const [selectedCourse, setSelectedCourse] = useState(null);
    const [students, setStudents] = useState([]);
    const [selectedStudent, setSelectedStudent] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalRecords, setTotalRecords] = useState(0);
    const [pageSize, setPageSize] = useState(20);
    const [message, setMessage] = useState(null);
    const [messageType, setMessageType] = useState('');
    const [searchText, setSearchText] = useState('');

    useEffect(() => {
        fetchCourses();
    }, []);

    const fetchCourses = async () => {
        try {
            const response = await api.get('/api/courses');
            setCourses(response.data);
        } catch (error) {
            displayMessage(`Failed to fetch courses: ${error.message}`, 'error');
        }
    };

    const fetchStudents = async (courseId, page = 0, size = pageSize, search = '') => {
        try {
            const response = await api.get(`/api/students/courses/${courseId}`, {
                params: {page, size, searchText: search}
            });
            setStudents(response.data.content);
            setTotalRecords(response.data.totalElements);
        } catch (error) {
            displayMessage(`Failed to fetch students: ${error.message}`, 'error');
        }
    };

    const handleCourseSelect = (event) => {
        const selectedId = event.target.value;
        const course = courses.find((c) => c.id === parseInt(selectedId));
        setSelectedCourse(course);
        fetchStudents(course.id);
    };

    const handleStudentSelect = (student) => {
        setSelectedStudent(student);
        setShowModal(true);
    };

    const handleCreateStudent = async (newStudent) => {
        try {
            await api.post('/api/students', newStudent);
            fetchStudents(selectedCourse.id);
            displayMessage('Student created successfully!', 'success');
        } catch (error) {
            displayMessage(`Failed to create student: ${error.message}`, 'error');
        }
        setShowCreateModal(false);
    };

    const handleUpdateStudent = async (updatedStudent) => {
        try {
            await api.put(`/api/students/${updatedStudent.id}`, updatedStudent);
            fetchStudents(selectedCourse.id);
            displayMessage('Student updated successfully!', 'success');
        } catch (error) {
            displayMessage(`Failed to update student: ${error.message}`, 'error');
        }
        setShowModal(false);
    };

    const handleDeleteStudent = async () => {
        try {
            await api.delete(`/api/students/${selectedStudent.id}`);
            fetchStudents(selectedCourse.id);
            displayMessage('Student deleted successfully!', 'success');
        } catch (error) {
            displayMessage(`Failed to delete student: ${error.message}`, 'error');
        }
        setShowModal(false);
    };

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
        fetchStudents(selectedCourse.id, pageNumber, pageSize);
    };

    const handlePageSizeChange = (event) => {
        const size = parseInt(event.target.value, 10);
        setPageSize(size);
        setCurrentPage(0); // Reset to the first page
        if (selectedCourse) {
            fetchStudents(selectedCourse.id, 0, size);
        }
    };

    const handleSearchChange = (event) => {
        const {value} = event.target;
        setSearchText(value);
        if (selectedCourse) {
            fetchStudents(selectedCourse.id, 0, pageSize, value);
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
            <h2>Courses and Students</h2>

            {/* Message Alert */}
            {message && (
                <Alert variant={messageType === 'error' ? 'danger' : 'success'}>
                    {message}
                </Alert>
            )}

            {/* Course Selection Dropdown */}
            <div className="mb-3">
                <label>Select Course: </label>
                <select
                    className="form-select"
                    onChange={handleCourseSelect}
                    value={selectedCourse ? selectedCourse.id : ''}
                >
                    <option value="" disabled>
                        Select a course
                    </option>
                    {courses.map((course) => (
                        <option key={course.id} value={course.id}>
                            {course.name}
                        </option>
                    ))}
                </select>
            </div>

            {/* Search Bar */}
            {selectedCourse && (
                <Form.Group className="mb-3">
                    <Form.Label>Search Students:</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Search students"
                        value={searchText}
                        onChange={handleSearchChange}
                    />
                </Form.Group>
            )}

            {/* Create Student Button */}
            {selectedCourse && (
                <Button className="mb-3" variant="primary" onClick={() => setShowCreateModal(true)}>
                    Add New Student
                </Button>
            )}

            {/* Student Table */}
            {students.length > 0 && (
                <div>
                    <Table bordered>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Courses</th>
                        </tr>
                        </thead>
                        <tbody>
                        {students.map((student) => {
                            return (
                                <tr key={student.id} onClick={() => handleStudentSelect(student)}>
                                    <td>{student.name}</td>
                                    <td>{student.surname}</td>
                                    <td>{student.email}</td>
                                    <td>{student.phone}</td>
                                    <td>
                                        <ul>
                                            {student.courses.map((id) => (
                                                <li key={id}>{courses.find((course) => course.id === id)?.name || 'N/A'}</li>
                                            ))}
                                        </ul>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </Table>

                    {/* Pagination */}
                    <Pagination>
                        {Array.from({length: Math.ceil(totalRecords / pageSize)}).map((_, index) => (
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

            {/* Student Detail Modal */}
            {selectedStudent && (
                <EntityModal
                    show={showModal}
                    onHide={() => setShowModal(false)}
                    entity={selectedStudent}
                    fields={studentFields}
                    masterEntities={courses}
                    onUpdate={handleUpdateStudent}
                    onDelete={handleDeleteStudent}
                />
            )}

            {/* Create Student Modal */}
            {showCreateModal && (
                <EntityModal
                    show={showCreateModal}
                    onHide={() => setShowCreateModal(false)}
                    entity={{}}
                    fields={studentFields}
                    masterEntities={courses}
                    onCreate={handleCreateStudent}
                />
            )}
        </div>
    );
}

export default CoursesStudents;

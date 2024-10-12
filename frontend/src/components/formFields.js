export const employeeFields = [
    { name: 'name', label: 'Name', type: 'text', required: true },
    { name: 'surname', label: 'Surname', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'phone', label: 'Phone', type: 'text', required: true },
    { name: 'salary', label: 'Salary', type: 'number', required: true },
    { name: 'department', label: 'Department', type: 'select', required: true },
];

export const studentFields = [
    { label: 'First Name', name: 'name', type: 'text' },
    { label: 'Last Name', name: 'surname', type: 'text' },
    { label: 'Email', name: 'email', type: 'email' },
    { label: 'Phone', name: 'phone', type: 'text' },
    { label: 'Courses', name: 'courses', type: 'select', multiple: true },
];

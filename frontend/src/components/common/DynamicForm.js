import React, {useEffect, useState} from 'react';
import Select from 'react-select';

const DynamicForm = ({fields, entity = {}, masterEntities = [], onChange, setErrors}) => {
    const [formData, setFormData] = useState({});
    const [formErrors, setFormErrors] = useState({});

    useEffect(() => {
        if (entity) {
            setFormData(entity);
        }
    }, [entity]);

    const handleChange = (e) => {
        const {name, value} = e.target;
        const updatedData = {...formData, [name]: value};
        setFormData(updatedData);
        onChange(updatedData);

        validateField(name, value);
    };

    const handleSelectChange = (selectedOptions, fieldName) => {
        const selectedValues = Array.isArray(selectedOptions)
            ? selectedOptions.map(option => option.value)
            : selectedOptions ? selectedOptions.value : null;

        const updatedData = {...formData, [fieldName]: selectedValues};
        setFormData(updatedData);
        onChange(updatedData);

        validateField(fieldName, selectedValues);
    };

    const validateField = (name, value) => {
        const field = fields.find(f => f.name === name);
        let error = '';

        if (field.required && (value === undefined || value === null || value === '' || (Array.isArray(value) && value.length === 0))) {
            error = `${field.label} is required`;
        } else if (field.type === 'email' && value && !/\S+@\S+\.\S+/.test(value)) {
            error = 'Invalid email format';
        } else if (field.type === 'number' && value && isNaN(value)) {
            error = 'Value must be a number';
        } else if (field.type === 'phone' && value && !/^\+?[0-9\s()-]+$/.test(value)) {
            error = 'Phone number can only contain numbers, +, -, (, ), and spaces';
        }
        setFormErrors(prevErrors => ({...prevErrors, [name]: error}));
        setErrors(prevErrors => ({...prevErrors, [name]: error}));
    };


    return (
        <form>
            {fields.map((field) => (
                <div className="form-group" key={field.name}>
                    <label>{field.label}</label>
                    {field.type === 'select' ? (
                        <Select
                            isMulti={field.multiple}
                            name={field.name}
                            options={masterEntities.map(entity => ({
                                value: entity.id,
                                label: entity.name,
                            }))}
                            onChange={(selectedOptions) => handleSelectChange(selectedOptions, field.name)}
                            value={field.multiple ?
                                (formData[field.name] || []).map(id => ({
                                    value: id,
                                    label: masterEntities.find(entity => entity.id === id)?.name || 'N/A',
                                })) :
                                formData[field.name] ? {
                                    value: formData[field.name],
                                    label: masterEntities.find(entity => entity.id === formData[field.name])?.name || 'N/A'
                                } : null
                            }
                        />
                    ) : (
                        <input
                            type={field.type || 'text'}
                            name={field.name}
                            className="form-control"
                            value={formData[field.name] || ''}
                            onChange={handleChange}
                        />
                    )}
                    {formErrors[field.name] && <small className="text-danger">{formErrors[field.name]}</small>}
                </div>
            ))}
        </form>
    );
};

export default DynamicForm;

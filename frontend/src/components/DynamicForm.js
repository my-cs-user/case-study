import React, { useState, useEffect } from 'react';
import Select from 'react-select';

const DynamicForm = ({ fields, entity = {}, masterEntities = [], onChange }) => {
  const [formData, setFormData] = useState({});

  useEffect(() => {
    if (entity) {
      setFormData(entity); // entity değiştiğinde form verisini güncelle
    }
  }, [entity]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const updatedData = { ...formData, [name]: value };
    setFormData(updatedData); // formData'yı güncelle
    onChange(updatedData); // dışarıya güncellenmiş veriyi ilet
  };

  const handleSelectChange = (selectedOptions, fieldName) => {
    const selectedValues = Array.isArray(selectedOptions)
        ? selectedOptions.map(option => option.value)
        : selectedOptions ? selectedOptions.value : null;
    const updatedData = { ...formData, [fieldName]: selectedValues };
    setFormData(updatedData);
    onChange(updatedData);
  };


  return (
      <form>
        {fields.map((field) => (
            <div className="form-group" key={field.name}>
              <label>{field.label}</label>
              {field.type === 'select' ? (
                  <Select
                      isMulti={field.multiple} // Çoklu seçim kontrolü
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
                          } : null // Değer yoksa null
                      }
                  />
              ) : (
                  <input
                      type={field.type || 'text'}
                      name={field.name}
                      className="form-control"
                      value={formData[field.name] || ''} // formData'dan değer al
                      onChange={handleChange} // değişiklikleri yakala
                  />
              )}
            </div>
        ))}
      </form>
  );
};

export default DynamicForm;

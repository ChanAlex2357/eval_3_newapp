CREATE TABLE calendar_event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME NOT NULL,
    all_day BOOLEAN DEFAULT FALSE,
    background_color VARCHAR(20),  -- Ex: "#ff0000"
    employee_id VARCHAR(100),      -- Optionnel : pour lier à un employé
    event_type VARCHAR(50),        -- Ex: "leave", "shift", "meeting", etc.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Si tu as une table 'employee'
ALTER TABLE calendar_event
ADD CONSTRAINT fk_employee_event FOREIGN KEY (employee_id)
REFERENCES `tabEmployee`(name)
ON DELETE SET NULL;

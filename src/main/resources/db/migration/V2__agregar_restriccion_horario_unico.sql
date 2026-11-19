ALTER TABLE citas
ADD CONSTRAINT uk_servicio_fecha_cita UNIQUE (servicio, fecha_cita);
-- Insert sample vehicles if the table is empty
INSERT INTO vehicles (id, type, capacity)
SELECT * FROM (
    SELECT 1 AS id, 'Bus' AS type, 30 AS capacity UNION ALL
    SELECT 2, 'Minivan', 12 UNION ALL
    SELECT 3, 'Sleeper Bus', 25
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM vehicles);

-- Insert sample routes if the table is empty
INSERT INTO routes (id, source, destination, vehicle_id)
SELECT * FROM (
    SELECT 1 AS id, 'Mumbai' AS source, 'Pune' AS destination, 1 AS vehicle_id UNION ALL
    SELECT 2, 'Delhi', 'Jaipur', 3 UNION ALL
    SELECT 3, 'Chennai', 'Bangalore', 1 UNION ALL
    SELECT 4, 'Kolkata', 'Bhubaneswar', 2
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM routes);

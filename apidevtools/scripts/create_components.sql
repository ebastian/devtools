DO $$
DECLARE 
	total integer;
	count integer;
	newId bigint;
BEGIN
	total = 10;
	select count(*) from component into count;
	LOOP
		count := count + 1;
		INSERT INTO public.component(creation, death, description, name)
		VALUES (current_timestamp, null, 'Description of Component ' || count, 'Component ' || count);
	    IF count = total THEN
		EXIT;
	    END IF;
	END LOOP;
END $$;
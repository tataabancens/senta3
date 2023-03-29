import { useEffect, useState, FC } from "react"
import { Button, Grid, Tab, Tabs } from "@mui/material";
import { upload } from "@testing-library/user-event/dist/upload";
import { ErrorMessage, Field, FormikProps } from "formik";
import { createDishFormValue } from "./forms/CreateDishForm";

interface Props {
    props: FormikProps<createDishFormValue>
}

const UploadImage: FC<Props> = ({props}) => {
    const [selectedFile, setSelectedFile] = useState();
    const [preview, setPreview] = useState("");
    const {image} = props.values;
    const {handleBlur, handleChange, errors} = props;

    // create a preview as a side effect, whenever selected file is changed
    useEffect(() => {
        if (!selectedFile) {
            setPreview("");
            return
        }

        const objectUrl = URL.createObjectURL(selectedFile);
        setPreview(objectUrl);

        // free memory when ever this component is unmounted
        return () => URL.revokeObjectURL(objectUrl);
    }, [selectedFile]);

    const onSelectFile = (e:any) => {
        if (!e.target.files || e.target.files.length === 0) {
            setSelectedFile(undefined);
            return
        }

        // I've kept this example simple by using the first image instead of multiple
        setSelectedFile(e.target.files[0]);
        props.setFieldValue("image", e.target.files[0]);
    }

    return (
        <Grid>
            <input type='file'
                required
                id="image"
                name="image"
                onChange={onSelectFile}
                onBlur={handleBlur}
            />
            { errors.image ?
                    (<p style={{color: 'red'}}>{errors.image}</p>)
                :   (selectedFile && <img src={preview}/>)
            }
        </Grid>
    );
}

export default UploadImage;
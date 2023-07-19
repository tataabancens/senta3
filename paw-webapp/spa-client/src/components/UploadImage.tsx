import { useEffect, useState, FC } from "react"
import { Grid } from "@mui/material";
import { FormikProps } from "formik";
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
            if (image === ""){
                setPreview("");
            } else {
                setPreview(image as string);
            }
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
        <Grid container>
            { errors.image ?
                    (<Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}><p style={{color: 'red'}}>{errors.image}</p></Grid>)
                :   ((selectedFile || image !== "") && <Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}>
                    <img style={{objectFit:"cover", width: 250, borderRadius: ".8rem", aspectRatio: 1, borderStyle: "solid", marginBottom: 20}} src={preview}/>
                    </Grid>)
            }
            <Grid item xs={12} sx={{display:"flex", justifyContent:"center"}}>
                <input type='file'
                    // required
                    id="image"
                    name="image"
                    onChange={onSelectFile}
                    onBlur={handleBlur}
                />
            </Grid>
        </Grid>
    );
}

export default UploadImage;
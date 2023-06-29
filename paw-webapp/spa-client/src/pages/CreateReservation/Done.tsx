import * as React from 'react';
import Typography from '@mui/material/Typography';
import { createReservationFormValues } from './CreateReservationPage';
import { FormikProps } from 'formik';
import { useTranslation } from 'react-i18next';

interface doneProps {
  props: FormikProps<createReservationFormValues>  
  secCode: string
}

export default function Done({props, secCode}: doneProps) {
  const { values: {date, hour, qPeople} } = props;
  const { t } = useTranslation();

  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        {t('createReservation.step5.stepDescription',
        {
          date: date.toString(),
          hour: hour,
          qPeople: qPeople,
          secCode: secCode
        })}
      </Typography>
    </React.Fragment>
  );
}
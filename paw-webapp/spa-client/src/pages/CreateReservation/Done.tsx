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
  const { t, i18n } = useTranslation();

  const formatDate = (date: string) => {
    const dateParts: string[] = date.split("-");
    const year: string = dateParts[0];
    const month: string = dateParts[1];
    const day: string = dateParts[2];
    if(i18n.language.includes("en",0)){
        return `${month}/${day}/${year}`;
    }else if(i18n.language.includes("es",0)){
        return `${day}/${month}/${year}`;
    }
    return `${month}/${day}/${year}`;
  };

  return (
    <React.Fragment>
      <Typography variant="h6" gutterBottom>
        {t('createReservation.step5.stepDescriptionPart1')}{formatDate(date.toString())}{t('createReservation.step5.stepDescriptionPart2',
        {
          hour: hour,
          qPeople: qPeople,
          secCode: secCode
        })}
      </Typography>
      <Typography variant="h6" color="secondary" align='center' marginY={5}>{t('createReservation.step5.pointsDisclaimer')}</Typography>
    </React.Fragment>
  );
}
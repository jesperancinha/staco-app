interface Description {
  value: string
}

export interface StaCo {
  id?: number;
  description?: Description;
  year: string;
  value: string;
  currency: string;
  type: string;
  diameterMM?: string;
  internalDiameterMM?: string;
  heightMM?: string;
  widthMM?: string;
}

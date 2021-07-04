import {StaCo} from "./staCo";

export interface StaCoResponse {
  staCoDtos: StaCo[];
  currentPage: number;
  totalRecords: number;
  totalPages: number;
}

package com.moovapp.riderapp.utils.retrofit.responseModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lijo Mathew Theckanal on 26-Jul-18.
 */

public class ViewCurrentRideResponseModel {

    /**
     * status : true
     * message : Rides Lists
     * count : 6
     * data : [{"ride_id":560,"ride_trip_id":375,"ride_type":"live","future_date":"2018-09-18","future_time":"","ride_driver_id":2,"ride_booked_on":"2018-09-18 16:15:56","ride_booked_on_date":"2018-09-18","ride_booked_on_time":"16:15:56","ride_amount":"250","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":"","ride_seats":2,"ride_from":"kazhakoottam","ride_to":"Muhaideen Andavar Jumma Masjid","driver_details":{"driver_id":2,"first_name":"remya","last_name":"Krishnan ","email":"remyakrishnankutty@gmail.com","institution_id":4,"institution_name":"University of Ilorin","phone":"9446172550","phone_country":"+91","gender":"female","vehicle_no":"Klo2-1092","verified":0,"u_device_id":"","car_model":"Hyundai Elantra","car_capacity":8,"license_no":"02/08/2018","license_expiry":"02/08/2018","dob":"02/08/2018","ratings":0,"wallet_balance":"0","image":"http://themoovapp.com/manage/uploads/userpic/9227df6425b815c8.jpg"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.57117,"lng":76.86625000000001},"end":{"lat":9.28372,"lng":79.1538}},"poly_line":"y`is@a}ctM@?vBbEjCbE??DCl@[`@QPI^U\\Wf@_@^U\\[ZQd@WvAs@XKb@S??Uk@o@oBSq@GUYoA]oB]iCIcAKaAEy@Ce@A_@?c@@k@FcB?K?I?K??@k@C]CsA@W@K@IBEDE@CFEKc@AIAK?GAKGgB?M??EOVMvBoALIr@[dCmAj@a@n@a@h@YDA??^MHCb@U\\O`@SVMb@Yx@a@NIXM|@_@\\MF?j@KZCZE`HYxAGhCIdDMrDI^AF?tCGdAE`@AN?RAfACjAALAv@ATANAr@AVAZALAvBKv@Ev@EfEQ~@GhBGhFWlDM??bCGjEIrEGtDGvBEnACdHE~@AvJIBAxBEd@At@Ev@Gb@Gx@OBAZG`@Kt@WTI^Qb@StA{@vCsBjBmA`BeAnMiIFGhBkAf@[`@Y~AcAlFkD\\WnDoCxAqAtBmB^]^]v@}@r@u@vLoLtEgE`CyBlBgBr@u@r@u@bCkC`D}Cr@q@hEqDdA{@rGuFxCeC`DkC|CkCBCjD_DpLaKzCmClAeAx@s@vCoC|F_F`B{AjB{BRSHIRU~@_AJMFGjAsAdAqA~@kATCDItAoB|@qArAgBDEHORYdA{AVc@`B{Bp@{@r@{@DWv@aAh@q@|BcCtCqC~@_AbDiDfAgA^]zB{BbBaBDCt@w@dBiBRKFGZc@ZW`B_BTUlAgAJKFIXYvAsAJKnBoBz@{@b@c@jCmCjAkAh@k@z@{@b@c@`@a@pAqAlAgA`A_AVWlAgAz@u@f@g@LKBG@A@Cd@e@FIHKHMFIFOPMHELILIHIh@e@~BsBf@c@NMHGJE@?FCPSx@u@jCaCDCdA_AJId@c@vAoADEjB_BnAiA??XL??nCgCb@_@r@o@RUr@o@nAiA??p@_A^m@FKBO??BClDiDx@y@RQhBeBLKn@o@bCyBTSzBmBrB{AFGHMpAkAZ]BCLKLM???A?AAA?A@??A?A?A?A@??A@A?A@??A@??A@?@A@?@A@?@?@?@?@?TMHE^Yn@e@NMDEDEPIf@i@VU`@YZONQLGHCNGXIZKXERGNCRCdAIf@AD?P@P?X@|BNtCHvCJbBGpC_@d@IZE`@GRC`D_@^E`@GfC_@lDmA~BaAb@WjB{@t@UPEJAD?fA[JE^QxB_APOfAg@HEp@[FCnCoA~BkA`Ae@jBs@b@U\\Of@Ur@]~@g@jAi@xBcAlAo@XMJEJEVMRMRKRG~@g@vAo@|Au@p@[~@c@RI`Ae@TKx@c@DENIpG_DhAk@DCrAo@jAk@p@_@r@Y`@SzBkAXM|BiADAj@WZQz@c@nB_AxAs@d@UFCnBcARIrAq@NGJEbAg@fAk@dAi@nAm@DClB_ATG@AnAq@x@c@t@[fAe@TKZOt@_@d@SRKfAi@t@_@`CoApAw@RMt@m@f@c@r@m@TYRSz@eAPKn@{@RY|@kAZc@^k@X_@dAsAV]PUJI`BcBb@a@Xa@j@g@\\[lA_A`Au@fAs@~@i@h@Wt@[JG`A_@FCj@UnIwCjBq@lBs@fBi@v@UdAW`Dw@|A]nEcAtCs@pCs@fOeD~@QtA[b@MfDeAj@QLG|@_@`CkA|BuA~H{F|LmIf@]dAw@lBsAjAw@fGeEpKyHp@i@rB{APQt@k@pEcD~CgC~@w@fBuAv@k@BClDmClFaEVQ|@o@fAq@dD}Bp@g@n@e@hAy@XUh@c@bAm@bAy@NKNIjBy@|@_@~H}Cp@YrDmBtA_Aj@k@d@w@`@_@dCaCf@m@^a@BCBCPSPS^c@PQHKPQ??@C?C@CrA_B`E}EbB_C|CcF`@s@|C_GPERc@dAgD|@yCh@oBr@iCb@kBtBwIlAeEpBmH`@eAb@oAd@_An@iAJQJQn@uAnA_CtAeC~@_BXe@Zc@dAeB`AwA`BgCdA{ApAgBb@q@^u@Vk@Vw@Z{@h@gAb@w@h@y@x@oAt@kAj@y@`AiBh@{@p@oATe@P]~@_BdDwF|CgFpCqEfDoFfEqH^_@x@y@NOHAJCLGZYl@w@vA{BlAaCRg@t@cBP_@Pa@HOHIHIBADGHE??D@b@DfBZlA^`Bn@rA^t@Jr@L`BJn@ArASbAKh@E`@?\\D\\Fr@XxAb@Z@l@Dt@D`@JZPt@|@`@^\\V\\Pt@^z@\\h@\\jDxAvB~@hBv@r@^ZHpAX|AVf@Jh@Ph@T??ZUTOj@Y`@Qt@Y`A_@pB}AhAs@fAs@NOHMj@_AlBsDtAgC`B_CD[B[?UE]Mo@Mc@Qi@EOCOAM?G?G?I@G@K@GPg@h@wAtA_CL_@BILk@?EBEBENUr@}@tAyARUb@{@\\eCBg@@KLm@Nw@Jk@Jk@B[@]B]?e@@k@E}BAa@Ca@C[CQK_@GQO[m@sAc@_AGOIOEOEUMcAGw@Iw@Ms@WqAg@}BM_@GSAM?E@I?E?C@C@CBGBEFIFELEFCNCTEbAIHCNEJELGNMLQFQDSHi@RkBDy@@cA@mA?_A?e@DWLWd@U`@IJAbAQp@IVETEN?NAJ?D?BBD@BDDFBJDRJl@FPHPHLFDNFR?L?N?LCNA\\Af@@|@?bAEx@?v@@l@Bj@GxAQl@Gn@ITGXA^?^@@?^@p@HZJTFPHRPJJJLp@fAb@x@FFLFZP\\H`@Fb@DdBLz@Ff@DP?R@H?HAPAFAHCHCJEFCHEZODC@?~@e@TMPKNKNMVSb@[PORQRQVWp@}@r@y@DGt@{@p@w@`@i@Zc@Zc@^c@hA{An@y@^e@TWXYz@eAp@w@p@y@TY\\e@Z_@\\g@hA_B|@mA^g@h@q@b@i@h@s@v@iAh@y@~@iAv@eA\\k@f@w@h@w@l@o@b@_@`@]X]f@q@d@o@JMZ]V]z@kAdAmAfBoBv@{@b@g@n@o@TULMh@o@v@w@l@o@X[d@g@h@k@Z]NOd@i@jAsA`AeAX[`@c@`@g@T[V]v@aAh@w@~@aAn@q@p@u@`AaAf@i@`@e@HIPQz@w@xAwAp@u@|@aAbAkAjAmAZa@Z]j@o@`AeAp@u@j@m@h@m@j@m@|@eAhAqAhAiAvAkBbAiAd@e@h@g@dAiAx@y@hAsAfAmAFG|@}@f@i@l@Ut@Kz@Sf@Y\\_@PONSJUR_@J[V]|@k@hAy@\\Y^q@^q@^m@|@oA`@{@H]?]AYAUB[F[\\mA^oAHUz@oCb@eBx@qCbAeD|AmFTu@j@oBd@kBLo@@KCKGEMEUAm@Ak@E??gAUOCKGGECIM]W{@[u@Q[UYQOQQSOQMWG[M]Mw@W??Le@LSZ_A^}Af@cB`@cBJ[RiAZkAEyFDuC@cDAeA@qADq@Jc@Nk@Lg@Ja@BW?S?OCiAAaA?}@Ao@A]CQCIEKKQIK]]IMGIEMAO@M@QLq@@CNSTUh@Wf@W`@U\\_@Na@Rk@Z_APq@@Cb@eAdAsB`@s@`@IFSPi@X{@Lo@Lg@Fc@Ni@d@y@`AkAT]Pg@Lg@Bm@@EAq@EmAOeAQy@Q}@Ey@?m@Bu@D{@HiAHq@JsAF{ADu@?e@@a@@a@@[D]D]TuAFg@Fc@Ji@Jk@RkBDg@?UDO@???WkAOqAM{ACUM{AEu@Cq@Aw@AoACgA@oADmADq@Dy@L}@FSHUHQJQXc@VYZa@b@c@JILI^M`@K\\Cx@KZK^GVGRINKJIDIBGBK@K@QH{@DUBMFS\\}@v@wAn@kAx@uA\\c@n@q@t@i@zAiA`@c@??\\NpAd@TFpA\\hBZrAZf@Jb@FNBTDXBX@rF\\t@LF?H@HAH?F?D?B?D?LFZJ^Pv@\\~@f@|@f@XN\\NVJ\\JRBLDRFJDPDh@N\\JXB\\BzAHtABtA@|ABbBFr@FLBl@F~@LvB^bANZD\\BX@RATAVAn@En@GZAZ@b@FZBP?XAT?b@?b@?b@@TAFADCDGIuASgBO_AUsBE}@A[@Y@UBMPe@f@_AV_@P[ZeALq@HSFCDAPCPCNATELGHEHGFGHKDKFQ@MFILMPQXQRMPMJIPIJCLCJCR?j@@^DXDVDNDFFHFHFD@FBHBH@NARCTENCJCNEBCDC@A@ADCBEDI@EBGDMHi@Hi@Lq@F_@Ji@FUDMTe@Tg@Xm@Tk@p@}ATs@V}@Je@HUNWLKLGFCLIPMRQJIFIFKFK@I@EJa@H[DSDOBKDKJOLQT_@HOFMFODML]Vi@Ve@T]T[v@cAZe@@C?ABC@GBQDc@@UBQBUDYHa@F_@DU@K?OD_A?g@?u@?}@BiAB[x@aCFi@RqABu@Bm@Fc@JSNWb@m@b@u@f@s@Zg@Rq@d@_ApAsB@CHMNId@[HIHMPWtAgCx@qAXu@DIBKBIBEBEFEFGDEHKBGDGDIDIBIBc@Jc@`@eBj@mBDEBAHAJ?X?V?b@CLCRE^OPKNGPUFQRCLOBIDKBEFSDOPm@XkABQBQBIBGDIBEBAFARA^CXERIJGJIPIROLMPMVKZO`@Up@WLCRCR?B?b@?P@TCJATIREd@AH?H?BAFCHEDCFAFAJ@JBJ@N@F?HCHGDCDCJMHMDGFEHGJCHCFABAFEFIFIDG@GBI?E@M@WBW@E@G@IBIFODKDMJ_@FUBOBSDQDMFQFOLWLQJOFMFODQBOLk@Ji@Jg@Nu@He@D_@Dk@@YBUDUDM?C?A?A?A?AAAAAEA[KQIMEKECCCACE?AAC?IAKBc@@Q@MDIBIVk@`@y@Pa@JSFIDG@ABADAFAD?J?D?B?BAB?@A@ABCBEDMHQJ_@BOBQ@U@OBMBMHOFKNMLKHKHOLSJSHMb@u@P[T[l@{@h@gAZq@JQHKJE^Ox@Q\\GREPG\\OVMZQTMj@]f@_@h@i@NULSLUH[F[HSHUV_@P[JQFK?A@C?A?C?AAE?AAACCEAQEKCKCK?KAO?O@gA@S?SAWCUCYEm@M{@SECCCCA?E?E?C@i@?U?QAK?MCSAQAOAM?Q?K@G@EBWDQDQJWNYDIJSHUFQDQBKFa@BY@{@By@?QEY?_@@[Ba@TuCP{CRwBHs@Lw@DWH[Ns@BGBG@EBGLULULSJOLSNa@DODMFUD]BWDk@?CJm@?EBQ@m@@o@@W?U@O?i@Be@@UBQDO@QBU?[?a@?WA]CMGMGMSQWQQKUKSESCWAS?_@@a@@U?WAMAMAGCGECGEM_@iBi@qCWeAEOAK?K@k@?q@@y@?i@E{@?c@?[?UDu@Ds@HeALw@Nw@Hm@Ba@?GACCCECGEICs@U[QcAi@USACAK?M?Q@O@SHyCAKEOQa@g@_By@wBEOKc@Me@Os@Mq@AQ?OEiAAc@AW?e@B[BWBKBM?K?KGm@@M@QHe@Hy@Bw@Cg@@QDQTi@Pc@BO@K?MEk@Cc@EcAAs@?w@@oBBeA@eAAKACACACAAAACAA?s@CCAAAAAAAAi@Eq@?WA[CWEa@AWAc@@w@D{@Fw@Hu@Bg@Be@@g@Dg@?y@A]A]AOCUASA[?UBSFODKHITYJSDO?M?MA_@A_@AYAa@A]?SB[Fi@Hg@@g@BqACo@?q@@i@D_@Fi@Hu@N{@@E??X?fCE`DKvCCV?Z@l@Br@D??CWAICMGSIWIQUe@GQISQe@IUEQI[GUOmAQuAIu@KuACUASCUSoBAIAI?OA[AW@Y@UB{@Fm@Dq@Dg@D]Fi@Dc@NyAFu@LoAHu@Lw@LiAXwBHq@F_@BQ@O@O?A@a@?o@As@?k@AoACsBC_A?YE{@?[BoAJwBPmDJwAPoCZeEh@wGPeBNwADk@Jm@Fa@PcAX}AXsA\\qBVcBNw@TmAHc@H_@Js@Ze@n@qAr@_B\\}@Rc@h@kAJUFSFi@DW?m@?IAa@E]CMGOKSMSIQOSMOMOO]Oc@M[GQGQAW?QF[Vw@jAcCbBcDNSPOLCLAd@?n@B|A@|ELr@CNCJGPM~@qAlBwCX_@`@o@FOBODWBW?gA?}@?M?w@?wB@cAAgC@a@BsBDiBFmAH}@BQNa@HOJQNMPGfAUdBWl@Gl@Cz@A\\?`ABz@Dt@DbANT@f@@\\A`@C|@GpAQxBW`BQfAQ`@M^MVKZOPMZUJMNQVg@b@_AP]P]l@gATa@j@m@jAiAV]Pc@DQBUDw@F{@Bs@H{@DYJw@Dq@JgAD{@Bo@Fs@@}@Fy@Jm@Ps@h@oBz@uCd@sAFQJOHMPSXU|FqEh@c@|AuAlA{@NEHCLC\\AX@XBXDRF\\LZNLHNHJHPH^LFFHDLDHB|A\\zBh@|@FD?LCXKrAeAp@k@RYHQHSNYLQJILKBADAB?RAXAV?N?DAHAFCDCDGBE@C@E?M?KAUCQEK[s@EIYq@EOEMAY@u@@a@?UDOHSNWf@k@h@g@jBmBj@o@d@c@^a@\\c@n@oAd@cAf@iAhBeE`@y@Ti@l@qAb@_Ap@_B`@{@HWX}@Rk@H[^eALWP]V]V[|@iAlBkCj@y@NSd@q@VYXY\\Ud@Yn@UbAYBAREDADAtAU~@Q`@KnA[^O`Aa@lAw@JGZSPMb@WxByAvBwAp@g@h@i@f@e@t@w@zAuAfBoAb@YJG??DENQT]`@w@b@oA^kAb@oAl@gBl@eBj@cB\\eAXmANu@Jw@NcAFo@@S?MCOYy@k@cBu@iBSs@Im@AO@k@F_BPmBZcCD]DSHOj@w@f@e@h@q@b@]p@Yv@Wf@W`@YJKROJMHOBK@SDg@FcAHq@H_@DUJYHQT]NOFMHMJQFMBQB_@Eu@IuAMoBGk@EWKw@?M?SBWDWFSDKFMLQNKr@_@`A_@b@OXKf@Kf@K\\C`@Cd@Ar@A\\APAREPGPI\\SNMTSJKFIFO|CmJp@qBd@}AVoAF_@JiAVuEJiADcAD}@TqCBe@B_@BSF_AF_@BSTcANa@Xs@`AoB~@sBn@cANSl@u@jAeBd@o@f@q@\\q@\\o@Xw@VaA^_B`@}ANk@JYT{@\\uAb@qAf@eAv@wAj@_Av@{A`AiBp@uARg@La@Lq@N}@Hq@Fm@@MAuBAuB?}ACoA?K@i@B[DQDSJULSPQTOTIr@OdAWn@Ip@GhAEv@Gd@GLERIf@]`@[PQr@_AjAsBz@_B^w@L_@DWBWJmAXgBVsAJu@PgAF_@FKJOTSzCoArAg@x@]~@g@VUjA_ANOPQPWLULUFSF[BY?YCQAOCKAGCGAESYY[_@YUQSMYMYM_@MYMSIMIIG?ACCCIAK@QBk@Dg@H{@J[PUV_@R[LONSPMPKRINEp@OfAQl@KLEJGHOFKRu@BKLs@T_A@ATcAHWFUFMFGROLGLGZKx@G~@?l@@p@F|@Lb@FN?LAZG`@U|@e@fAs@f@a@b@e@Va@Ze@R]J_@Fa@F[B]Hc@BQBW?_@AWGy@Kw@Gm@C]?QBSDYF]J[Nc@R_@d@w@HMj@k@HGNQVQTOtAq@~@m@\\WLQNWHQXy@F_@D]I?CsCCaBEkBCaDEsBIiECk@AQCKEKSc@]k@[i@s@gAm@eAOYKSQ[MYU_@QU[]WWKMEMAI?Q@[Be@@g@EYES[kAQm@S{@Ok@ISIQ[[gB}BCICICCCICKCKO{@[oBAIIw@Ey@?ICgAAcBCoA?GCgBE{A@]@YJm@Je@JYVg@`@q@TY?ATYNU@I@I?K?_@E[e@sAG}@CiAFwBBiC@iCCkACkA[cCGo@o@_GKk@Qm@w@mC_@iAQq@ACAOCk@?qA?sB?oA@_BDaAD]BYHq@TkBd@uDVuBJo@LaA`@uAf@uBXoAJw@Dy@Dw@Bc@FsC`@qLHuB?_BEaF@yB?s@@o@IkA?w@LyEDyDA{DCiF?c@JoICkBGu@Gm@]yBEc@Ek@Gi@SmBQcBKuBQ_BKw@OaAYaAi@mAg@eAc@iAGO_@sAc@{A]_Ba@mBGYGc@AU?SDm@Dk@Bq@Bi@@cA?}@AOEm@I_AEg@CUIo@K}@AEKs@Ky@WyBOqAYwBcBmNo@kFGe@CSAM??CYA]ZSf@c@h@e@RQ\\]VYJMJOLSLSVc@Ta@NYNUPYb@m@d@k@T[p@{@xAkBjCcDh@m@DGFKFKFMDKBIDKBKBOFc@Fq@Fc@P}AJy@F]FSDMNk@N]Vk@Xg@v@uArI}PFKHKDIHKDGFEVU`@[n@c@v@g@LILKJIVQXYRSPQJMLOP]JSN_@Ne@nAcE@APu@BIDKLa@Ri@~@yBZs@Xw@DMJ]Ps@Ne@|AoGXyAReALq@?CN_AR}APoAJgAPkBDW?ILcABMDQDQ^eARi@DQHUF]F[DYHw@LkAFc@DYH_@Po@ZcA??oDiCiBqAEEoA{@uAeAEEsB{AgEuDOM_AaAkAwAEGu@}@mBgBMMkAoA_@]c@QWIAAYQa@e@We@]mA]oAi@uAmAqCc@aAQ_@cAaCe@u@[]YOo@Ya@UcA}@s@e@_DyAWKyEwBaCcAcBo@kA]y@[]W_@[MUUc@Yk@UYWSaAi@cAe@o@[[WUa@Y_@_@]i@_@e@Yg@QcA[s@W_@]k@u@w@oAq@}Ac@_BI[]o@Sa@}@kAoAuAaBwAYUa@_@qAs@sBcAaAa@q@_@oA[iB]aAWYKu@c@sAo@}B_Au@]m@_@yAcAaBoAIEKMSSWWKOMYQo@Oq@Ms@K_ACUG[IWUW[YWS[Ku@Ui@Om@Mc@?i@@i@ESM??KQCg@?mECyCEu@Ao@CWEWQ{@GUAYCw@?a@C[Ow@OqAYqAE]Mg@]uAK}@IWa@aA[u@ESBOLO\\O`@a@r@aANc@H_@B]C_@G[a@gAc@cAa@_AGc@Ik@Om@Ke@Yk@Wa@Y[c@i@c@_@e@]a@]i@Yc@_@K[Ws@_@}@e@kAW_AI]??gDC??x@gFDg@CsCAk@?o@AgBIaIGqI?w@CmBEu@Mu@aA}Dk@eCY}@GSKSa@a@a@]k@m@CQ?]Fq@RgCPuEl@}CdB_@|@[NkDH}CTmBp@sAJiBC{@\\}BVmDJmAe@wAo@e@eAe@iAWe@QAAEAKBeCO]QQk@Ws@OqAAmAFkAA]I[I]uA{@g@]e@YYUyIgNMM??DU?i@G}AMuAYgAWsACW@c@No@NSjAe@vAm@??c@oE}@oI??WF}@]mGkB}@YcFwAe@Ck@BmIpC{DjA_@HYG[KyH{BmBc@e@GeB@qBYcCoA{BWq@S_GuD??B[Bs@HkBJ{BFiATqGDy@@_@?SAq@A[IaAEYQo@Su@Ww@K[I]ESCc@A[@[@]BqA@[?i@C[Ow@Qy@Qm@i@wBQu@GYGUGUI_@AOE[GuAEiBEmBEqDE}DIqKOeJIkF?MA]?{@CiACw@I_AGg@Oi@sAmE}BqGeBmEu@aBoA}CO]O]Og@KUSe@GOGMKKKGMGOGOEOGKEIESIWOGCKKKKYe@S]Yc@k@}@IUO[Wy@[kA[wAWmAS}@Mo@Oo@Oe@Uw@[oAIi@Qu@Mm@Uc@SWQSUSk@c@c@c@c@[YUYUYYSWMUGYEYAc@A}@BuA@]DWJ[JUHIHEHCHCF?H?RA`@ATGLORg@b@oAd@eCPaA\\_B^{AZgA\\oAVeATcARaAJmAF{AG{AE_@QcCGm@Iw@M_AWqAU}@W}@Yy@Uo@Qm@[_AQo@K[Qq@Qk@I_@K]K[KU[k@Wc@[c@QYOYGMGMGKEQEUE_@Ey@YqIE_DAg@?S?Y@S@YD_@J]Rk@Xq@Vm@~@kBz@kBb@aAn@sARc@N[^s@Ra@BEDKJQDKPWLSDI^c@HMv@s@|AkAHEFEPOTUNOHIHKl@k@Z[h@e@JKPS@CBEDMDIBI?E@KBGAEBa@DYD_@BO@G@EBABCDE??IYEQIa@E[E]Gg@E_@C]AWAY?Y?W@WB_@Do@Ds@Dy@FkAHuAFw@F{@@Q?K@I?I?K@M?M?MAQ?QAQCSCQEQEOGSGSGSWk@Qc@_@y@Sa@Yi@_@k@Ye@Yc@Q]OYO][q@m@uA{@qBaAcCw@cBg@gAw@}AQ[Sc@s@yAcAuB_@u@KUa@w@MWKSISa@y@S_@O]OYe@qAkA{Ce@kAi@yAc@iAM]q@gBa@iASm@Og@Oe@W_Ai@iBWaAy@qCs@}BUy@g@_B[eAq@uBMa@Us@s@}BaDsJy@eCAEM_@Ww@k@}A[{@GKEKIOOUOSQQi@i@a@a@a@_@a@c@aBmB}@eAg@k@[]mAkAo@k@oAiAiD}CmAiAeAaAgAiAmAoAeBgByAwAoDoDmEiE_DyCcAaAwBqBoBsBqAoAs@s@mAgA{@{@w@w@{@y@sAoAm@m@i@m@QSMQCECGAECE?EAEAE?EAE?G?E?G@K@OBg@@YAS?UCUCUCUI]GYOa@ISWa@S_@Wa@[g@OSQWQUUWMKMKQKQI]Og@QgA_@iAc@KEKCe@KmAUqFaAiAM}@GqBSaAQkA[kAc@q@[]Mc@Ss@Yc@]??kC_AaA_@y@]aCeAkBy@gAa@{@WaBc@iEeAgAYe@M_KyBqDq@mCa@EAgAQqHmAmDk@QEuEm@eBYc@Kc@MiAa@k@SUKUKi@QgBc@SGi@Ge@Es@EiDEoDC}AGgJs@cEYgFa@eBMsCU_COeBMqBQw@I_AMuAW}Be@u@SqA]UKi@UWOi@_@y@q@c@c@iAuAoAyAs@y@[Y]Yc@Ye@[c@Qq@Ym@SA?i@Oc@Ki@Mm@Mq@QiAWm@Ma@MYEKEwCs@oBc@yA[cFcA}A]u@QYIq@UMEMGw@a@m@_@yCoBWQQK[SWOeD{Bq@c@{CoBm@[{@a@_A]_AWk@Og@Ic@G{@Mi@Ew@CuEQkAE_@Aw@E{@Ic@K}@W]O[O]Q]Ss@g@m@g@mAaAwAmA_@[w@i@g@[iAc@i@Os@Qy@MsAMiBKyBIsBGi@AmDOs@EcAImCWuD]cBOqAG_BGuCCoECmACq@E_@Gq@Ky@M_@K}@Uk@WqB}@kB}@iAm@IEs@a@KIu@o@q@y@Yc@Q]KW_@{@Us@Oo@W_AIUi@oBWy@Qi@c@eAGMi@_Ao@w@i@q@i@i@_@[e@_@e@[g@YaAe@QG[IKEc@Qm@Qu@QaAQqAOA?iAOeCW_BSy@Ke@Is@O}@U_A[w@_@g@WQK]UcAw@[Uq@k@g@a@i@c@cAq@w@c@q@]MEs@_@}Ay@gB{@kCqAyBcAkAa@o@Ss@We@Oc@Qg@Sm@SgBk@{Ac@qBc@OCk@My@Q{@Om@Im@I{BUkGc@k@E_BMcDU_Ga@gAIkDUo@Eo@EwIm@KAUAUC_BKaAIq@GaAMm@K}AUmC]qH}@eC[sB]_AQ}Bg@}@S_ASe@Gg@G_BQ}D_@iCWiCUuAKkAIcBGcBEkBEsACcCGwBIkCMqEUmAEsAI}BQcFa@i@Ew@Is@E{AKy@EC?kAIqGi@kDWaDWC?_FYgAGkA?gAAaC@mBCcCGaBGk@CcCG_CMkAO}AYEAgA[i@QcAc@kBcAkAaAGEeAeAs@w@s@_ASUQUuAkBaC{C_AoAyC_E_CaDuCuDkA_BiA}Ai@y@g@w@sA}BwCiFwCiFeAgBqAaCkBeDkBgDIMaCaEw@wAiAoBsBwDQY_@o@_@m@_@o@IKW]s@_A[a@a@i@c@g@i@q@}@gAiAuAwE{Fo@aAi@cAg@aAYs@mB_Fy@qBq@sAYa@g@o@y@}@[Yu@k@yAgAuBsAoBcAuBy@y@]_A_@aAa@eAg@{@i@_Aq@c@]oB{A{@g@cAk@gAa@kA_@eAWeA]kA[mA_@u@Y_@Og@Wm@]o@_@eAm@_Ag@c@Sc@Su@[OEc@Me@OWK_Bk@uAc@s@QMCWESE[GUCi@Iu@I{@GcBMcBIe@Cy@CuBMMA{AIeAEm@GqAGcAE{@G_AGi@Cw@Gs@Gy@Ks@Ms@O[Gq@Oo@Ow@QYIaAS{@SmCq@yCs@eCk@cAWs@Qq@Sc@Ui@Yu@c@m@g@g@e@_@e@MOS[]m@Wk@_@}@a@gAi@uAc@iAi@sAe@oAUm@[eAU_AWeAKm@Mg@Mm@c@kBY_AUo@We@[e@U[[a@CAe@c@q@g@s@e@_@UYQcBaA]OYQk@[g@Wc@Ws@]_@OWK_@Kw@U{@MeBQy@EaAGi@Cs@GMAGAeAIkAOsAUq@I_AKeAGG?{AG{BEiACeBCg@Cw@IaAIaCe@qAWsCq@aB_@iAYCAcA[cA]{Aq@gBw@iCgAgBs@MGoC_AyCeAm@WC?g@Ok@MaAMmCa@yBWwBUsEk@}AWo@OAAc@OeAc@y@a@qBmAiEqC{FwDw@g@ACyAmAeD{Ci@e@m@g@u@i@w@a@g@Sa@Mi@Mg@Mc@Ec@EO?WAY?[?u@B_@BYDa@F_@Hm@Ni@R_@PULULi@^SPSN_@^]\\oAtAsDbEEFu@z@w@z@mBvBqC~CkAtAuA~AyAbBiBrBqB~BcBlBo@v@_BlBmDvDeApAWZaDtDeBpBo@p@c@`@CDOJSPIFg@^y@l@kGlEy@j@_BdA_B|@oAp@_B|@kAf@iA^i@Jc@HaALk@@cABeA?C?e@EUE_@Gm@KoA[sBg@YGeDw@yJkCeFiBgC_AwFuBuBw@sEeBuB_A{BcAuHiDoKqEuGqCgFcCiB}@kI}DqCqA_D{AsAm@kAm@OIiAk@wA_AgAcAu@{@_BoB_BqBcD_EeE{EaFiFEIeGiGw@w@iCkCAAa@e@oBiBqAcAuCyByAkAyAaAgBeAoBcA}As@uAq@YOsDgBsAi@}@Yo@Ok@Mg@I{@Ku@GkAIkAIm@CMCk@EcAGc@CwCQuBK}AGeDMcM_@kDMwAAOAM?eDEy@AoGGmBAsBCeACSAu@GiAMaC]aFy@_Dq@sBk@eA]qE{A}@]kBq@aD}@yGmBy@UyGgBsA_@UG}Ag@E?}C{@uBm@gAe@iBaAsC_BkEeCmDqBsAo@e@QGCk@Qw@Os@KaAGgAAi@@gAFw@F_BJgCNaA@iAAqAMaAM_B_@_AUoFyAuBo@iA[q@OeDaAqEwAaEyASIwFyBGCiBw@_DmA{Ak@uB}@iAm@UQUQa@[c@_@m@m@cAcAKMuAwAc@_@o@m@_@Wo@c@i@[s@_@kAg@q@Uk@Q]K_@Gw@Qy@MQC{@Ks@Ga@A[CqAKiBS_Ca@aAQ[GSEcAQWEoB_@kB[cBYyAYqAUkAS{Ba@_AI_@CKAUAs@?g@?Q@cAFy@L}@PoCn@_@JeE`AwDv@yDr@g@H{FdAcBXeB\\eBZgAP}APuAH_@B{CRsBLsCNcDFyCBoGDaA?i@?}BCuAGk@EeAIwAK_M_A}BMyAQ]E_@EMA]Ku@Qe@Qe@UoAq@qAs@sAs@s@a@c@YgBgAiBgAQMSQk@e@SSSSmBoBw@y@aAcA_@a@sBaCACcAgAeAkAkDuDQO{OiOe@a@OOMK?Aq@s@IEe@_@o@e@e@Wa@Ua@QwAk@_Bg@iBo@c@O}@YwAe@OEcDgAgA[gBc@{@Ue@KyBi@qCy@iAe@q@_@ECe@W_@UuCaCsAgAcBsAy@s@qAgAc@_@}AqA_Ay@s@i@u@g@eAq@gAm@}BqAy@i@s@c@WSaAy@cA{@aBoAgAy@}@m@}@i@_Ai@]SgAs@iAo@mBkAqBmA_CwAyBsAyB}AkA}@o@a@oDcBuFkCy@a@eEiBeAe@cAe@iD}A{@_@iAg@mCoA_CeAUK{BcAsCqA_Aa@a@Oa@M]Mc@Ky@O}@Q_BUkFw@qASkCa@w@Qo@O[I]MWKSIWOSKe@[WUOOOOQQOSa@o@QYQ]Wq@Y}@c@cB_@{AES_@yA[mA]uAYsAMw@SkAQyAUaBUcB]kCIi@K_AOmAg@mDUmAUuAs@kDc@eC_@oBg@iCq@qDo@gDQy@Oy@_@sBWsAc@_Co@cDe@aCEUy@kEs@{De@{Ba@{BUqAKi@Y{BIw@IeAKmAOaCKkAASAQCYO}BKuAEo@KgAWwAQkAe@eCiBgJg@sC_@mBe@kCo@eDg@cCUcAW}@Og@[u@a@{@i@gAk@kAk@mAo@qAO]a@y@KOUc@o@gAU[a@g@e@e@e@_@}@q@}AiAwAeAgA{@ECqAcA_CiBmB{A{AmAaAs@c@YoD}BoAw@sDkCu@g@w@i@aAw@a@_@MKs@o@u@w@g@m@k@q@e@s@m@aAe@w@[i@[k@}@iBiAeCiAcCm@qAcAyBc@cAe@_AO]]s@c@y@EIc@m@Y_@QSUSUSUS[QYOw@_@o@Sm@Oy@Mk@Go@EeAGOAe@Ae@COAq@E_BIkBKaBIu@E{@Gg@Ck@Ae@?g@@i@Ba@Fg@H]HYFo@RaAZYJc@LkBp@k@RuA`@q@Pk@N[D]Fc@Da@B[@a@@_@?m@Cc@CiBOgGi@a@Ee@Cg@E{AI}BEg@Ae@?aEQoBGcDK_CGgBI{AEyAEoAAU@Y@w@Ds@Fu@Fy@JkBT{C^iCZ??AT??c@DcC\\_@Ds@F[De@BaAB}@@c@?e@@aC?eA?_A?aAB_A@yHLkBDs@@_ABU?_@DwBJ??CDCDCDCDEDEDEBGBC?EAE?CAECCACCCCCECCAGAAAA?A?AAA?A?AAG?I?G@I?G@GBG@IBC@EBC@EBCBCBCDCJe@BMJm@Hm@NgANoAR}ABOv@iGJ_AHs@BYNoAHcADc@LeBL}BF}@@a@BaA@q@?Y?mAAgAAi@Am@CiACkBCyACwAGqDAmAAy@AiACkAC}BCyCGmDA{E?}E@kA?aA@yA@oE@uA?_C@qB?k@BsB?QBqABeABa@FcB@GPuFL{Cz@gNBq@JgBHeAFs@VkCf@_F^qDh@aFTwBFk@?Ch@uEN}AJy@B[LoALkAHw@LaAZwC\\kDRkBPwAP{AJy@Jo@L{@Ns@Le@Ru@\\_AVm@Tc@hAqB\\k@t@aA~@kA|AoB`@g@b@o@`@q@\\w@Nc@Lc@J[DSBQBOHiAHkUAgJCuBC_@Ei@Ik@Oy@Mc@M_@O_@Sc@QYOYQUSWWW_A{@i@e@_@Ys@g@kA_Au@k@WUWSAAKI][OOEEEGOSMOKSMQ?AQa@KYIYI]EUEUC[CYACAUAUAMAICUCo@GkAEkAKeBuAkZi@yLc@iFCWSaESiDGcAGoAaAcPU{FOeCCiCAkBDiD@sG?uC?wDKaBQuAQ}@a@iAi@mA_@q@w@eAUYo@_AaD_EGIg@q@g@o@SYo@u@U[OUgAoBUo@Os@Km@Go@CyAA{@Aa@GqAEaA]oBUm@Ym@m@aAY_@i@q@w@_AeLaNqBaCUWaAkAgAoAo@w@q@w@}@gAq@w@}@kAo@u@s@w@s@y@c@i@q@y@i@m@o@s@{@cAQQk@s@e@i@Y[Ya@QUSYm@aAYe@We@}@wA}@{Ay@qA}AiCu@oAKQkBuC}@cAmAgAeBy@kBm@aBa@oBi@}Am@s@[gBgAmAgA{@_Am@}@}@_BYq@]_AWgAa@oBMoBCm@AyA?c@^iDRmATy@d@sAdAeD`@eAVy@TaAp@eCz@gELg@rCuNvA{Hx@uF^uERaF@_@?kFAuAIuCi@uGkA}IWaAW{@c@eAg@cA}CmFYg@m@_AmAqBeAgBoAuBs@oAiAiB}AqC_BqCQYGOO[ISK]IWEOAI[mAG[EYGo@?GQsAIeAQmBSqBIu@Ee@SqBMiAW}BQcBGq@Q}AOqAQcBMmAIgAM_AI}@CYCQAOAOCy@Ak@Aw@?g@LgEd@wJFcBBYDi@DU?CDYDSBO@KF[DWDOFSDSFWH]HWNa@j@gB`@uA^oAjBcGr@kCDMd@_BLc@T{@HWZ{ALo@BS@G@G?M?E@O@Q@Y?c@@aAMw_@CgEEoLCqDAqC?g@C_GCcECkOAcA@_A@}@B_ABo@Bs@JmBBm@Fy@PcB\\mDnBiRPiBzAeOHgAJ_BDw@Bu@D}ABoB@_A?{@CiEGkOQe[EwIAsECmECmDAgDAiCAeCAu@SsZCiMCaDCyGEoEAiDA_CAeBAoAAcECwCAkA?[?g@CmBAaGAmGCk@AaDEmFEyCAsCAyC?kC?]?wAA_A?}B?ICwECiDAyFCsBCeDA_C?kA?e@?WCiCA_A?MAuA?EA{C@{@@wBLoFFoCD}A@YHaDDuAD}BFgC@SFsCF_CBgAHaDPaHHsCLoFBy@@eAAk@?a@Aa@E}@Gw@Cg@E]CWKu@Gm@Q{@Io@YeBeAgG{@{Ek@kDiA{GaAsFU_By@yEI_@M{@i@}C_@wBWcBUsAW}AWyAKk@c@kCo@wD}@eFq@_Ee@oC{AyIWuAq@{DcBiJ_AqFe@mCY_B[gB_@uBa@_Ca@{BUoAs@wE[{Ac@aC]mBMo@k@mD]sBEQOu@Mq@I[Mc@KYOg@Ug@OWU[w@{@a@a@a@[MKSKc@Us@_@GEy@_@s@[]McAa@AAqBy@_@SWM[QKEMKYSQOOMUUWY_@g@QWMSu@kA_@m@_@m@m@}@U]QWSWMQc@e@MM_Ay@q@i@gAy@uBcBoCyBiCoByBeBQMQMUOm@i@c@c@a@e@e@o@Ye@O[M[Yw@I[Kc@E]G_@Go@G{@I{A]}DWyBUoBUqBWwBQaBWuBUcASs@{@gC_HiOg@iAUc@a@w@Wg@e@k@SWYY_AcA}@y@iAcA{_@y]wJ_JkCcCoAiA_@a@kAkAUUqBwBkBsB_BgBiAmA{@_As@y@{@_A}@eAmMmOo@{@GGc@s@a@o@_@s@QYmBoDqAaCaCgEs@mAe@aAg@cAk@wAe@_AGMc@eAm@sAaB_EyAgDiBiEWk@Se@Wi@??K?SAO?cACiCKwCMOAGAq@E_AEa@?OAaABoADo@@G?aADK?M@}@Fc@BQ@Q?K?U?WAo@A{@C]CQASEKAMCUGYIu@QYGMAYEGAu@KWCWC[Ce@AKAM@Q?O@g@DO?Q@S@UAUAOCQCgAYsAa@YIKCICSC[Go@Im@IYEAAMAKCKC[IKE_AWc@M]K_@Is@QIAWGSEQCKA_@CUAu@EeAEkAEa@AY???OAkBvC}C|EkBfCqBjCsCjDaGzH}b@pj@w@`AmBfCyBxCi@r@_A`AqAfA{@j@cAf@gAb@kA`@m@Ny@RkAPeBRaDNcBHsAHq@@a@?iBFqAFg@?g@@eCAo@?_@EEBE?E?I?c@Gg@GsAWg@Kq@QyAi@iAk@]U}B_BsAeA{EwDqA_AcCgBcBoAMIwAcAECaCoBoB}AgDqCqB}AuCcCsDyCgA}@c@_@i@c@_BqAgBuAgBwAyCcCqB_B[UuBcBiCuB{CcCgCmBgCqBcCoBiCsBcDiCcCoBoB_BmCwBeCsB_BoAqC{BqAeAuBaByCcCyAkAi@c@gCqBmCyBqDuCSQyCaCcCmBgCqBuQyN_DoCWS_B}AsA{AqCwCeCkCuDaEqAyAm@aA]m@Wo@{@qCg@eB{@oCACS}@I]Y}@]kAUo@Uk@q@iAs@}@q@m@gAs@aAc@eA_@y@QgAOa@Ek@Cq@AgABa@BoARmATiCp@eAR]HuCr@oCn@m@Rc@J}GbBoBf@uAZy@LuFxAaDt@gFjAsBd@kAXyG~AsKhCsCp@wBf@oCr@oCr@_B^e@JaCl@iFpAC@sJdCo@N[H??EYCg@@G?GP}@ZaBRaAXyAF]Hc@Dc@Fw@B{@@o@B[BYHs@Hi@J_AHm@Fa@j@sBHWJa@P{@L}@B}@BwB??BgCBwABoBDaCBsCF}BBaAHsARqD^eFxAgTZkFJeBF_CDeCDoBBcABiAHgBFkAFy@P{B^uDZ}BPwAlAeKX}BdAgJVwBVaC\\wC^sCV{B\\cCh@uDl@qEzAeKl@eEp@aFb@{CFy@Be@@g@A_@Cm@C]E[??Ki@Uq@Wo@KYAA]e@UYYY[Y_@Yk@[cBw@gCgAoDyAgEiBaDwAeDuAm@We@Qs@W{Cm@eCe@yB_@sAYq@Kq@KoBSyBKuBMm@Ik@Go@MaAUmD}@mHkBaIsBmG_BiCq@wCy@cD}@sCgAgCeAkGaCKCcA]KGMGWMSIaA[{CcAeAa@sAo@oAm@wBiAoAk@k@Yo@]k@Yo@]o@a@i@a@eAu@wAaAuAy@}@g@_Ag@oNwGeGyC}J_F{FsCaAg@oB_A_B{@aAi@uCwByAiA_BkAqAw@iAs@qAo@g@Ww@_@q@YoAe@sHwCu@[s@_@eAs@wAsAyAoAwAgAmKsGcEkCk@[c@Q]Ma@M{@UiBSy@Ci@?aA?mDFoCDgD@_DGiDKkI]kBGwAEiAC}A?kDBcMRoEHw@@oBAuCAgCCa@Am@E{@G{De@y@Me@EwDe@_Ee@YEoC[kAO}@Kc@I_@Mo@S}@a@w@]w@]y@W_@KWEa@Ea@Eg@?}@@sFTaCHgDN{BHa@@_@Ak@?{CO_CImACkBA}ADcABc@Ba@Ba@Dc@De@Jy@Pc@JuAb@_G~AoF|AcAXk@N_ATm@Jk@H_@BWByA@qHMsKW[?yDG}AEe@EWE[C]K[I_@Og@Yq@a@}AiAmA_AuH{F]USQKKIIEIEGEIEMCGCIE]w@kLMeBIu@Go@GkAq@oCaAgCoBsDwB{DwBeEq@wAg@w@c@o@Y_@a@a@_@[aAq@c@SiAe@aC{@eBs@OGQIk@Yk@e@WYSWWc@Yk@]iAc@aBcBgGeAqCKUm@iAu@oAW[_@][Ug@W_@Oc@MWEm@Ka@E_@?wABaAT_@H[Jm@PcMhDeIxBqIrBsFlAqARg@HoALyANuBNsGf@gADc@?_@?kACe@Cc@CwFYkKi@gBMkAKyAM}AOoBOw@Ei@Ca@A]?]@[BYFe@He@Li@Vg@Rk@X_@T_@VWTa@b@UXS`@Q`@M^Mj@GRK^CDGRGJGHIJMHSLKBA@KDMB[B{FF??y@{BSw@I]I]EYGe@MiCEi@MsAEWAGMi@Su@]mAUm@I]CMOm@S}@[{AMe@IWKWK[MYQYKQOUQWSU]g@AAWa@M[KUGOIYMc@Ka@a@wBa@kBm@sCSs@eCoIQy@Ow@Io@K}@KqASyBU_DMuAMqAKgAGy@E_@EWE[Kc@Kc@IWUg@]w@We@}AmCeB}Cs@oAS_@Sa@Qe@Oe@Qq@I_@YcBWwAYkAW}@a@mAc@mAi@cBY{@Qq@U_AScAQeAKk@Ge@Ie@C_@Ga@Eq@Es@Es@Ek@Eo@Gi@C[G_@Ie@K_@Me@Oe@Ws@[u@eAgCa@_AOa@M_@Og@So@q@qCYiA_A{Dw@cDw@qCq@aCc@yAQs@Mm@Kg@Ic@Ka@Ka@Qk@So@Qe@Oe@]w@yA}C}EkMmCyHsDkKeCyGiC{G}@_Ci@iAWc@S_@i@w@i@w@m@s@k@o@q@o@kB}Aa@[aBqAwAiAc@a@[[e@g@[_@UYuC}DeAyAyCeE_DiEcAsAaAwAm@_A_@o@Sc@Mc@O{@Ek@AU?_@@s@@gABqA?i@Cu@E[G[Oe@Oi@Qa@MSc@i@_@a@q@o@uAqAoAkAyAoA_Au@}@o@{H}EwFiDk@c@m@i@i@e@_@c@]a@e@k@y@oAkA_Bg@u@y@gAq@cA[k@q@kAa@u@Yc@[c@k@s@[]iCsBw@i@yAuAkAcAw@q@QQa@c@}@y@_@WWSe@[u@a@{BcAgAa@u@OuAQ]ImAOcBWkAWeBg@iA[SGUGUK]Q_@Ww@k@u@s@s@k@{@q@sB{AmFwDkBuAqByA{@o@a@[g@_@o@k@k@o@g@q@MYMWUm@Ww@c@iBo@oC[}A]sAG]K]IYQe@c@eAEWoAyBAA_@o@KQQ[a@q@aAkBcBkDoC{Ge@gAa@_Ak@eAyAmC{CsFs@oA[i@S[S[g@m@o@q@i@g@o@e@o@c@q@c@{CaB}@e@y@_@}@[m@Qe@Kq@ISA]Ck@Cm@AoB?m@?iIDq@Bi@Bg@Fg@Fg@Jo@NwA\\KDg@Jm@Hm@Fw@Bu@?q@Cy@Ku@Mi@Me@Og@Sa@U{@k@_Au@w@u@e@g@g@i@oBkCg@s@Wa@Wi@[w@Sk@Qq@Mg@Kw@]eCEUCOEMCIKMMMMIKEKEKCaAIw@IkAOy@Mu@Oe@Iu@_@a@Wc@Y}AgAqBwAs@e@g@a@a@_@WUQUg@q@_AoAoAgBu@aAa@e@c@c@k@g@i@a@y@o@u@k@m@g@USWYe@i@GIu@_AiGiIgEsFs@_As@y@}@}@yDkDi@e@e@g@]_@a@e@QWQUgBmCsB}CqByCWc@We@a@{@_@w@Ys@yAyD_CqG{@_Cg@qAUe@S_@Q[q@y@a@e@s@o@_CsB}@s@}@o@oA}@_Ak@s@c@}@g@_Bw@cAe@y@]gBq@mC{@wC}@sBc@y@McAM_Gs@}Ek@oBU_Fk@_BYSEYIoFqA]KSGWOWSYWg@g@??H]Ng@hAmDbAkCv@yBtAiEpBcG|DkLxGsS`B_FfCwHdBmFh@_Bj@uAb@w@vBgDpB}CzA}BXc@zAwBhFmHT]r@aA`BeCzBaDpBwCj@y@d@w@\\k@d@w@n@iAf@cAbD}GlB}D|B_FlAmCbAyBvA{C~@yBPe@Ti@z@}Bd@uAr@aCtA{E\\cAVw@j@kC`BkGZgA~@}CRk@n@cBn@}AfBwDhA}BrAqC~@iBv@cBn@oAFQ@K?G@GMOGEICQEq@G]COAyD_@_@Ag@CkACgA?}@@O?iBDkCDk@Aa@E]Eq@UYK[Sk@c@W[U[Q[IYIUGWEWE]AcALyAh@_FRgBRqBDi@Fs@DsA@g@Ac@Ck@C]WeC[wCM}AO_BSaDI_BIqBMmBE]CWCUCUEOESKa@IWIQGOMUOYMQOQSW[]_@a@g@g@o@m@i@g@y@s@eAy@kCqBEEiBsAaAq@uA_AuEsC_FwC_B{@iAi@qAm@cDwAyDcB_Ae@e@YWOYUYUQOOO_@a@i@q@y@eA}EmGcAsAKM[a@UUQOYUWOWMk@Yg@Qm@QsBg@w@Mg@Ok@O[MYMGEIGWUIKQQSW]g@{@aBwEoIgI_Os@kAmA{ByAwCGMy@cBqCcGyB{EACcA{BgAuCcAwCoAmDKQMWe@u@OUm@w@aD{DuBmCq@}@o@{@c@s@eAoBgAiBu@qAsDmGe@u@qCeEuByCq@cAc@{@Yo@Sk@Og@qAwEwAuFo@eC}@oCm@cBSi@i@sAUo@k@}AKa@Me@Ik@E[AIEc@IeBE{@EaBGsAC]AOMi@GSGOMSGMKMIKOQMKOKQKKEOI_@MWEYEm@G}F]k@CwAI}Je@QA}EQsAEgA@sAB}@Bm@@k@?_@ASASCa@I]Gg@QSK]Os@c@_F{CqHsEoJcG}AeAkDiCyGmF}IcHyMeKqFgEsEyDgAw@i@]c@WkNoH_Ae@oK}FQIuEaCgCuAk@]c@YwDoCwCyBoA}@aCgB]Yo@e@_@[{@y@q@u@s@aAgBgCwCcE}BsC}BkCeAiAKKkAoAMMm@q@y@w@{AsAeDoCg@c@CCmB_Bk@a@q@YaDw@e@KeD}@{Ag@s@SkE_Ae@Kq@Q}A]??FIDGNQbAiADE@A@CBC@C?E@E@c@Bs@@M@O?YAQAQ?KCSEUQw@?AOi@e@kBI[EYCWE]?OAO?]?QEYCUGWIYMe@Ms@COAMAQ?UC_CAo@AsA?[?I?I?I?U?Q@I?KBQBSHq@Hq@VeB@U@M@M@U@U?Q?Q?OHsK@aADa@@U@S?O?O?S?UCs@EqAEy@Ae@?QAOAKAOCUEa@CUKw@Gc@Kk@UgAi@wBQk@Kc@GQIUM[Qc@s@aBISGQGMEMEOEOCOCMCQG{@ScCMcA?GEWGg@CWASAYAW?YAkAAeBEoC?c@A_@?SAKAME_@Ge@EUCOAOEg@Cy@GiACaACu@?YCyBAuBAsACqACiACYC_@CSCWGi@I_@E]Mm@OaAKu@s@mEQ}AEm@Cm@Ac@Aa@@c@?g@@a@Ba@BYBYFi@d@eDZ{Bd@qD\\gCnAaJHqA@e@?Q?SCWAWGm@E]Ia@Mc@Oa@Ue@U_@kAuAwCiDsAeB{@uA_AmBoB{EqB}Em@wA[u@Uo@Sg@MYIWGUo@wBaB_Ic@qCy@aEKg@Qk@Oe@Se@We@OW]c@_@a@u@q@aAw@mDkCyBaBcBuA_DwCkBeBiBmBwAaBm@u@m@_ACCcAyAMSa@o@]g@q@aAMWMYIQISGUEUKi@CQG[QgAKc@EQGMWk@o@oAmCeF_CuE{AwC}@yBYw@Uw@S}@SaAK{@KkAIiBAaB@yAA{@A_@Ca@E[Mm@Oo@a@eB_Ia\\q@uCuGeZWgA}@wDgBiI]yA[iA{@sCmAuDaBiFgAgDcA_D{AuEk@eBSi@GQKUUc@_@m@g@o@k@q@EEgAmAeFyFiAsAkAoAkBkBoDmD}BwBeAcAi@g@e@i@a@e@[c@Uc@Sc@Se@Qm@Qq@ScAk@}DSmAQeAa@gBg@oBk@wBQq@Mu@SoAM_AMsAK{@Ku@Qu@Oi@Uo@Qc@Wg@k@eA_A{AeCgEQYy@wAaAyAU]Y_@eAiAyAaBUUSWSYW_@We@Sa@Qa@KYM[KYI]Y}AKy@O_BSwBKu@_@aCOaAM}@KiASoCEy@Gu@YiCc@{D_@iDWoBEc@CWCWA[CeAMgCKeCKuBCc@C[CUCQKi@aAaDIUQm@AGEOE[C_@?M?Q@]B[RqB\\iDLiAJiANiAZiBbBaJPeAHm@BYDY@]@Y@m@Aq@Ek@Gi@Km@Oi@W{@Y}@wA_EsAmDYw@Ys@aAmBmA_Cc@aAYm@]{@]_AWw@W}@y@aDg@qBSs@e@oA}@{Bs@_BYw@[{@a@gA]wA]_BQ{@SaASaAMu@I{@Gy@EaAImBMaBQqAYmBqC_Q[uBOcAIo@Ec@G{@CaAAGO}BQaD[wEUcDGeAEc@Ik@Ii@UmA_@sAyCcKuAyEm@kBYaAwDyKy@eC}AcFmA_EwAoFyAmFK]KWO[MWMSa@g@a@e@m@k@qAsAe@e@[a@QQACGGIMW_@Yc@eE_HuBoDu@wAwAmC{KoS_Wwc@KQiEuHuAaCuAmCiAyBkAgCwB{E[m@_@k@g@u@q@w@g@g@e@c@_EeD_BqAiAaA_@]a@c@g@i@e@i@oAwAQQQMKIMG_@Q]KuM{Dm@ScA_@q@YaAg@y@e@w@g@SIUKUGWI]G_@Ew@MYEQEQGSKQKQKQQMMQWMYQc@Oc@Ug@GOKMMMKKOIQKWK[OSKQKa@[SYMUQYMWMWKUIYK[?AGWE[Ik@Gi@QgBCe@AQCQCWAAEYCWAM?C?E?EBi@?E?C?CCUAS?GAa@BwDBiBBqFJiR@cGGg[CuW@eCFuC~@ck@`@wUj@o^@w@@m@Ao@Au@QqICaAA_@@_@@[BWD_@j@{Ex@wFz@}GJi@Li@Ps@Re@`@o@Zc@p@s@~@q@^Qr@Yt@QrAUz@CxBH~Ef@zEv@|E~@l@Jd@F`@D^Bf@@\\@\\AvAId@Iv@OjA[bBg@jBk@pBo@p@SpBm@nBm@zAc@jAc@`Aa@lAo@`B}@xBqA~BwAvBoAdBcApAu@jAq@x@c@x@e@fAg@rAm@xAk@jBs@hH{BbA_@v@_@n@a@n@g@p@u@b@q@d@{@^cAJ[Ja@DUFi@Ba@B]@o@GkDKmEA_AA_A?cABmAFgAHcAHiALgAnAkI\\gC@?Bc@Bo@?g@?g@Eg@Gi@Ii@E_@CYCW?Y?e@Dm@Hg@Fc@Do@De@DyBF_C?U?iACk@?O?EKeBEs@KeCG{ACo@COCQEOISEIIKGGGEMGOGs@Oa@GeAKsC[wNaBm@I]GKCIEIEKIIKKMCKCIAMCY?QGkDCaCIiFCoCAwBAoA@a@@Y@_@Ba@Fq@J}@ZcCJ}@LeADa@@Q?M?S?QAOAUC_@EYg@aDIg@C]Em@CmACwAAw@Ae@AQAOASEUKg@IUKYO]KQ_@o@k@_AKQKSEKGOCICKCIAKEYAQAUAO@O@W@Q@URsBBW@Q?O@S?W?QAKAQCOCQCUa@aCQkACUAKAK?KAK?_@@Q?O@O@K@IBOBQFYd@sBVcAFYHa@De@@Q?U?WAO?KCUEUGc@a@uBQ{@Gi@EYKwA[kEIeAKuA]oDGa@Km@Mk@WgAg@wBq@mCYcASu@Us@[eACOEQCMAOAYCu@Ey@KeEGoBGkBOoEAQGcCCyAEyACwAGmBAk@Ca@G]C[GYIa@Mi@c@aBYmAKc@G]G[_@oCE[G_@EQQu@_@oAKa@EMCOESCSc@sCCOq@oDKk@GYGUMe@IUMWAE_@o@a@i@m@{@_@g@[c@S[KOKSOYGSISCQIa@CYAM?OAO?Q@O@S@SFa@`AwFTaBViBNoAVuBJq@Fo@@Q@M@[@SASC_@Gm@eAkHQuACYAIKyAGiAGuAIoAMwAMiAMoAY{BGc@Mw@GYIYMa@Oa@gAsBmBeDkAoBc@u@AAGM_BoCyBkEO]IUK[Mc@Sw@Ou@Mw@uAyJKk@gA_Hk@kDKy@Im@C_@C]?Q?Y@W@YFu@NaBb@sFf@mHN}CPqD??{A@cCAa@Ac@Ai@EgBQi@KYIMCSGYMk@WeAg@uC}AuAw@e@Uq@Ue@M{C}@kIkCsDiAu@Oy@MeAQcBQaDY}@G_@E]Gm@MqOeDs@Mu@OYGWIMIGEEECEEGIUAE?I?K?O@]Dm@TyD@U@Y@]?OAKAICIEOIOKQOOe@e@aBaBw@y@iDmDmFuFqDsDcB_B[W_@Wo@a@o@_@_@SIGgFiDgIwFsA}@cIiFwKiH}HiFmBoA_FgDiAy@[Ya@c@oCwC{DmEsA}Ac@c@[Ya@Yo@g@eBoAQOMMOMMOcAeAcAeAsA{A_BgBqAwA_AiAaAgAwAwAeAcAuBkBsBsBiFcFmCmC}IyIc@g@mJyI_EaEcIgIkBcB}FkFo@i@SOGEICMEWIk@Ec@Go@K}Ci@qB]kCa@aEo@sDi@kFy@e[{E{FaAsJ{AcHgAqUmDgJoAwc@aHyDo@gKcB_C]wASmCa@iAWmPaEwTmFkNgDmA[}Aa@uAYa@I??@W@EAGCEaBiAkCgCuA_BiAsBkBeEiBeEeCcEAAaBaDiC}FuB{FmAyDkAcFCIwAuHcAgHiA_HuBkMcBgKcC_O}CqSwA_J}AkJw@aFs@}Do@kEYeBGYKe@IYMa@IYISSc@KSe@w@i@m@g@i@a@_@u@o@SQm@_@kBgAWM]Oe@Oc@IqA[kDs@mB_@s@QcB[eB[sJ}AsB]a@IwLkBgAQ??n@mBX_ATi@\\u@DKdAaCXw@XiAFa@D[RcA?EXsB?Cn@mEL{@LiAvAyLb@sC\\sA|B_HlB{Fz@iC|B}GdBgF^sA@EDSVcAdAgFVkAJk@ZiB?C^wB\\qBXgBR}Ad@oDL}ALsA@ITgDH_Ad@iGVcDNmCZoGNiCHqBHwAFcBFqAFg@LeAfAoHVoBf@mDjAuIdBmMz@eGb@{CVaDLmCDqA@YJuCJuBHqBLkENsDTqHPwE?ELsCBcARaEj@wOLwCZmJn@mP\\yIRqFDcA^wJ^_Jl@wOt@iRVsGZwVHaBDe@Da@F[No@h@gB`@uAfAcElCoJf@cBnIeZn@}BbDcLdEqN^mApB{GFWJo@VaBjAgHhAmHv@uEVuAHYTq@bBiEjFyNjIgTpBqFL_@jCiH~AqEPa@P]L[pByHlCwJpAmE\\}@zA}FvAmFJa@L[Pa@\\}@^q@rCiGx@aBl@oAlAmChBsDdAuBlByD|C}Fd@aAbCsEzC}FnDyG`HqMvAyCFOrBuD`D}FdFyJtBaEh@aAR]^s@h@cAtEsIjAuBj@gAXg@d@{@vAoCx@aBTg@Nc@F[Jo@De@Dk@Bm@Bu@?u@?g@Aw@?U?EA_ABgABs@Fs@Da@Fc@Je@?APq@Ni@Tw@BEn@_BN]LUd@o@NQj@m@BCp@s@r@g@v@g@vBeA`Bo@r@UnAY~D{@nDy@d@KXI`@KtBs@rBq@bAa@v@_@r@e@VUTUPQT[Xc@f@_AnA{CvCiGvAyCTe@nE_Jl@iAf@}@p@eAdDyFhK_RNWpDsGp@mA^}@^eAxAwERu@~@aDzDoMx@sChEmNnGcTb@yAj@uBh@gCnDeSnA_HnB}KBIdAcGxDiTZcBPk@X{@jB}EjEkL`@mAJ]Hc@F]BYB_@@_F@iBBq@Dg@DWHq@h@_CrFsVl@gCv@gChAsDd@aBFS`@eBBY@m@?o@?CDgD?a@D_AJg@Lw@\\gAp@_BJ[H]dA{FnBcM~@uFt@sEb@qCj@oDFu@Bg@?s@IuCo@cUEoD?aB?q@CmGEkEA_JAw@A]KgAGYKc@yBqIo@cCo@iC}@kDa@oBAMYaWOsOAs@?uBDqDLeINaKVgKF_DTuIJuCHmE?mAE_AI{@AEKi@U_A[}@sA{CeAsC}B}GgCqHEMEOCUCe@s@_S]mLAmFDsBPiC^iE`@eCRqA|BgJn@kCf@wBPs@H[Vu@dAaChAkCzBgFx@wBR{@Jy@FuAB_BE{@KaAWwAWmAO_@Wc@??A?A?A?A?A?CAo@Oi@Oa@MoA}@e@Wm@]aBeAs@g@s@c@uAeA??JpA??A?yAWuAOE?iAOuAKUAWAA?qAQg@a@o@U"},{"ride_id":559,"ride_trip_id":375,"ride_type":"live","future_date":"2018-09-18","future_time":"","ride_driver_id":2,"ride_booked_on":"2018-09-18 16:05:48","ride_booked_on_date":"2018-09-18","ride_booked_on_time":"16:05:48","ride_amount":"475.1","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":"","ride_seats":1,"ride_from":"Sreekariyam, Thiruvananthapuram, Kerala, India","ride_to":"Kazhakkoottam, Kerala, India","driver_details":{"driver_id":2,"first_name":"remya","last_name":"Krishnan ","email":"remyakrishnankutty@gmail.com","institution_id":4,"institution_name":"University of Ilorin","phone":"9446172550","phone_country":"+91","gender":"female","vehicle_no":"Klo2-1092","verified":0,"u_device_id":"","car_model":"Hyundai Elantra","car_capacity":8,"license_no":"02/08/2018","license_expiry":"02/08/2018","dob":"02/08/2018","ratings":0,"wallet_balance":"0","image":"http://themoovapp.com/manage/uploads/userpic/9227df6425b815c8.jpg"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.546990000000001,"lng":76.91396},"end":{"lat":8.57117,"lng":76.86625000000001}},"poly_line":"uids@ggmtMKUQo@I[CO??k@JOBIBQFSJOFSNKHIFEBE@C?K@m@@k@CC?m@E_@CE?IAKCGCc@QYOYO??MPGF]b@e@h@e@j@{@jAW`@IRCJCHCLAFAJETGbAAJCb@C`@CzAAr@A^ARALANCPCLCJGPKTIRGHm@bA}@zAA?Q^ITIXETMt@Il@Kf@W|@YdAI`@EJIZWbAO^ADM^GPGPCDcA~AeAhBWd@QZKPKLQTIHSRe@d@o@f@i@d@OLMHOHKFMDSFMDg@JcAVSHA@QFYJuAj@OFeB|@u@\\iAh@YN[Li@Vi@R[LSDKBM@}@JUB[BSDKBKDIDA@EBABEBGDKJEFGHEFO\\GLGJIJGHEBIDKDQHOHGBKHIHIJKLELCDEJCLCJCL?L?H@L@NBRHX\\vA@FDTBT@V?^@\\?RBt@@`A?n@CREXGXqAzEK`@Wx@IVELIROXU^QRA?QN]V]R[Ni@Ty@X_@PeBr@k@V_A^s@XMDOHOJWRIFa@Z_@^g@j@wAhBKNk@x@KPUXk@`Am@jAGL_@l@MX??EJCHAHAJ?F?HBT@RHd@FXJp@Jl@Pt@Ll@n@|BrA`FlAzFBRTfAxAnGXhADPDVBd@Fx@??Cr@OzEAT?RAV]~JS|CE`AA^Ex@A^IhBKnBGt@AHA\\A`@C~@?\\??DRBP[NwAn@c@TkAl@iAn@iClAu@`@OHiCzA???LFfB@J?F@J@HJb@GDABEDCDAHAJAVBrAB\\Aj@?J?H?JGbBAj@?b@@^Bd@Dx@J`AHbA\\hC\\nBXnAFTRp@n@nB??G?CACAk@eBs@mCCKCCCAAAC?C?SFq@Ti@Zo@h@m@d@o@l@e@t@s@`A]`@k@h@e@d@KJ??wBcEA?"},{"ride_id":489,"ride_trip_id":344,"ride_type":"upcoming","future_date":"2018/09/14","future_time":"5:10 PM","ride_driver_id":0,"ride_booked_on":"2018-09-14 17:11:05","ride_booked_on_date":"2018-09-14","ride_booked_on_time":"17:11:05","ride_amount":"1887.0","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":"","ride_seats":1,"ride_from":"Kazhakkoottam, Kerala, India","ride_to":"Sreekariyam, Thiruvananthapuram, Kerala, India","driver_details":{"driver_id":"","first_name":"","last_name":"","email":"","institution_id":"","institution_name":"","phone":"","phone_country":"","gender":"","vehicle_no":"","verified":"","u_device_id":"","car_model":"","car_capacity":"","license_no":"","license_expiry":"","dob":"","ratings":4,"wallet_balance":"","image":"http://themoovapp.com/manage/uploads/userpic/"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.57117,"lng":76.86625000000001},"end":{"lat":8.546990000000001,"lng":76.91396}},"poly_line":"y`is@a}ctM@?vBbEjCbE??DCl@[`@QPI^U\\Wf@_@^U\\[ZQd@WvAs@XKb@S??Uk@o@oBSq@GUYoA]oB]iCIcAKaAEy@Ce@A_@?c@@k@FcB?K?I?K??@k@C]CsA@W@K@IBEDE@CFEKc@AIAK?GAKGgB?M??EOVMvBoALIr@[dCmAj@a@n@a@h@YDA??JSHKx@e@JG??BELSLSP]DKPgA@]@IFu@JoBHiB@_@Dy@@_@DaAR}C\\_K@W?S@UN{EBs@??Gy@Ce@EWEQYiAyAoGUgACSmA{FsAaFo@}BMm@Qu@Km@Kq@GYIe@ASCU?I?G@K@IBIDKLY^m@FMl@kAj@aATYJQj@y@JOvAiBf@k@^_@`@[HGVSNKNILEr@Y~@_@j@WdBs@^Qx@Yh@UZO\\S\\WPO@?PST_@NYHSDMHWVy@Ja@pA{EFYDYBS?o@AaACu@?SA]?_@AWCUEUAG]wAIYCSAOAM?I?MBMBKBMDKBEDMJMHKHIJIFCNIPIJEHEDCFIHKFKFMN]DGFIDGJKFEDC@CDC@AHEJEJCREZCTC|@KLAJCREZMh@Sh@WZMXOhAi@t@]dB}@NGtAk@XKPG@ARIbAWf@KLERGLEJGNILINMh@e@n@g@d@e@RSHIPUJMJQP[Ve@dAiBbA_BBEFQFQL_@@EN_@VcAH[DKHa@XeAV}@Jg@Hm@Lu@DUHYHUP_@@?|@{Al@cAFIHSJUFQBKBMBQ@O@M@S@_@??@s@B{ABa@Bc@@KFcADU@K@GBMBIBKHSVa@z@kAd@k@d@i@\\c@FGLQ??XNXNb@PFBJBH@D?^Bl@DB?j@Bl@AJAB?DADCHGJIRONGRKPGHCNCj@K??BNHZPn@JT"},{"ride_id":488,"ride_trip_id":343,"ride_type":"upcoming","future_date":"2018/09/14","future_time":"12:00 AM","ride_driver_id":0,"ride_booked_on":"2018-09-14 17:10:39","ride_booked_on_date":"2018-09-14","ride_booked_on_time":"17:10:39","ride_amount":"1887.0","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":"","ride_seats":1,"ride_from":"Kazhakkoottam, Kerala, India","ride_to":"Sreekariyam, Thiruvananthapuram, Kerala, India","driver_details":{"driver_id":"","first_name":"","last_name":"","email":"","institution_id":"","institution_name":"","phone":"","phone_country":"","gender":"","vehicle_no":"","verified":"","u_device_id":"","car_model":"","car_capacity":"","license_no":"","license_expiry":"","dob":"","ratings":4,"wallet_balance":"","image":"http://themoovapp.com/manage/uploads/userpic/"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.57117,"lng":76.86625000000001},"end":{"lat":8.546990000000001,"lng":76.91396}},"poly_line":"y`is@a}ctM@?vBbEjCbE??DCl@[`@QPI^U\\Wf@_@^U\\[ZQd@WvAs@XKb@S??Uk@o@oBSq@GUYoA]oB]iCIcAKaAEy@Ce@A_@?c@@k@FcB?K?I?K??@k@C]CsA@W@K@IBEDE@CFEKc@AIAK?GAKGgB?M??EOVMvBoALIr@[dCmAj@a@n@a@h@YDA??JSHKx@e@JG??BELSLSP]DKPgA@]@IFu@JoBHiB@_@Dy@@_@DaAR}C\\_K@W?S@UN{EBs@??Gy@Ce@EWEQYiAyAoGUgACSmA{FsAaFo@}BMm@Qu@Km@Kq@GYIe@ASCU?I?G@K@IBIDKLY^m@FMl@kAj@aATYJQj@y@JOvAiBf@k@^_@`@[HGVSNKNILEr@Y~@_@j@WdBs@^Qx@Yh@UZO\\S\\WPO@?PST_@NYHSDMHWVy@Ja@pA{EFYDYBS?o@AaACu@?SA]?_@AWCUEUAG]wAIYCSAOAM?I?MBMBKBMDKBEDMJMHKHIJIFCNIPIJEHEDCFIHKFKFMN]DGFIDGJKFEDC@CDC@AHEJEJCREZCTC|@KLAJCREZMh@Sh@WZMXOhAi@t@]dB}@NGtAk@XKPG@ARIbAWf@KLERGLEJGNILINMh@e@n@g@d@e@RSHIPUJMJQP[Ve@dAiBbA_BBEFQFQL_@@EN_@VcAH[DKHa@XeAV}@Jg@Hm@Lu@DUHYHUP_@@?|@{Al@cAFIHSJUFQBKBMBQ@O@M@S@_@??@s@B{ABa@Bc@@KFcADU@K@GBMBIBKHSVa@z@kAd@k@d@i@\\c@FGLQ??XNXNb@PFBJBH@D?^Bl@DB?j@Bl@AJAB?DADCHGJIRONGRKPGHCNCj@K??BNHZPn@JT"},{"ride_id":487,"ride_trip_id":342,"ride_type":"upcoming","future_date":"2018/09/14","future_time":"5:07 PM","ride_driver_id":0,"ride_booked_on":"2018-09-14 17:08:07","ride_booked_on_date":"2018-09-14","ride_booked_on_time":"17:08:07","ride_amount":"471.75","ride_status":"booked","ride_payment_status":"unpaid","ride_payment_time":"","ride_seats":1,"ride_from":"Sreekariyam, Thiruvananthapuram, Kerala, India","ride_to":"Sreekariyam, Thiruvananthapuram, Kerala, India","driver_details":{"driver_id":"","first_name":"","last_name":"","email":"","institution_id":"","institution_name":"","phone":"","phone_country":"","gender":"","vehicle_no":"","verified":"","u_device_id":"","car_model":"","car_capacity":"","license_no":"","license_expiry":"","dob":"","ratings":4,"wallet_balance":"","image":"http://themoovapp.com/manage/uploads/userpic/"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.546990000000001,"lng":76.91396},"end":{"lat":8.546990000000001,"lng":76.91396}},"poly_line":"uids@ggmtM"},{"ride_id":361,"ride_trip_id":262,"ride_type":"upcoming","future_date":"2018/09/07","future_time":"12:00 AM","ride_driver_id":0,"ride_booked_on":"2018-09-18 01:11:40","ride_booked_on_date":"2018-09-18","ride_booked_on_time":"01:11:40","ride_amount":"478.9","ride_status":"booked","ride_payment_status":"paid","ride_payment_time":"2018-09-18 13:41:40","ride_seats":1,"ride_from":"Kazhakkoottam, Kerala, India","ride_to":"Sreekariyam, Thiruvananthapuram, Kerala, India","driver_details":{"driver_id":"","first_name":"","last_name":"","email":"","institution_id":"","institution_name":"","phone":"","phone_country":"","gender":"","vehicle_no":"","verified":"","u_device_id":"","car_model":"","car_capacity":"","license_no":"","license_expiry":"","dob":"","ratings":4,"wallet_balance":"","image":"http://themoovapp.com/manage/uploads/userpic/"},"drive_detais":{"distance":null,"time":null},"poly_lines":{"start":{"lat":8.57117,"lng":76.86625000000001},"end":{"lat":8.546990000000001,"lng":76.91396}},"poly_line":"y`is@a}ctM@?vBbEjCbE??DCl@[`@QPI^U\\Wf@_@^U\\[ZQd@WvAs@XKb@S??Uk@o@oBSq@GUYoA]oB]iCIcAKaAEy@Ce@A_@?c@@k@FcB?K?I?K??GKGIEGQOMGQGSIECAAKGQM_Aq@oAcA??EM@AlA}@b@]zAeAz@i@VMvBoALIr@[dCmAj@a@n@a@h@YDA??JSHKx@e@JG??BELSLSP]DKPgA@]@IFu@JoBHiB@_@Dy@@_@DaAR}C\\_K@W?S@UN{EBs@??Gy@Ce@EWEQYiAyAoGUgACSmA{FsAaFo@}BMm@Qu@Km@Kq@GYIe@ASCU?I?G@K@IBIDKLY^m@FMl@kAj@aATYJQj@y@JOvAiBf@k@^_@`@[HGVSNKNILEr@Y~@_@j@WdBs@^Qx@Yh@UZO\\S\\WPO@?PST_@NYHSDMHWVy@Ja@pA{EFYDYBS?o@AaACu@?SA]?_@AWCUEUAG]wAIYCSAOAM?I?MBMBKBMDKBEDMJMHKHIJIFCNIPIJEHEDCFIHKFKFMN]DGFIDGJKFEDC@CDC@AHEJEJCREZCTC|@KLAJCREZMh@Sh@WZMXOhAi@t@]dB}@NGtAk@XKPG@ARIbAWf@KLERGLEJGNILINMh@e@n@g@d@e@RSHIPUJMJQP[Ve@dAiBbA_BBEFQFQL_@@EN_@VcAH[DKHa@XeAV}@Jg@Hm@Lu@DUHYHUP_@@?|@{Al@cAFIHSJUFQBKBMBQ@O@M@S@_@??@s@B{ABa@Bc@@KFcADU@K@GBMBIBKHSVa@z@kAd@k@d@i@\\c@FGLQ??XNXNb@PFBJBH@D?^Bl@DB?j@Bl@AJAB?DADCHGJIRONGRKPGHCNCj@K??BNHZPn@JT"}]
     */

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("data")
        @Expose
        private List<DataEntity> data = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }



    public class DataEntity {

        @SerializedName("ride_id")
        @Expose
        private String rideId;
        @SerializedName("ride_trip_id")
        @Expose
        private String rideTripId;
        @SerializedName("ride_type")
        @Expose
        private String rideType;
        @SerializedName("future_date")
        @Expose
        private String futureDate;
        @SerializedName("future_time")
        @Expose
        private String futureTime;
        @SerializedName("ride_driver_id")
        @Expose
        private String rideDriverId;
        @SerializedName("ride_booked_on")
        @Expose
        private String rideBookedOn;
        @SerializedName("ride_booked_on_date")
        @Expose
        private String rideBookedOnDate;
        @SerializedName("ride_booked_on_time")
        @Expose
        private String rideBookedOnTime;
        @SerializedName("ride_amount")
        @Expose
        private String rideAmount;
        @SerializedName("ride_status")
        @Expose
        private String rideStatus;
        @SerializedName("ride_payment_status")
        @Expose
        private String ridePaymentStatus;
        @SerializedName("ride_payment_time")
        @Expose
        private String ridePaymentTime;
        @SerializedName("ride_seats")
        @Expose
        private String rideSeats;
        @SerializedName("ride_from")
        @Expose
        private String rideFrom;
        @SerializedName("ride_to")
        @Expose
        private String rideTo;
        @SerializedName("driver_details")
        @Expose
        private DriverDetails driverDetails;
        @SerializedName("drive_detais")
        @Expose
        private DriveDetais driveDetais;
        @SerializedName("poly_lines")
        @Expose
        private PolyLines polyLines;
        @SerializedName("poly_line")
        @Expose
        private String polyLine;

        public String getRideId() {
            return rideId;
        }

        public void setRideId(String rideId) {
            this.rideId = rideId;
        }

        public String getRideTripId() {
            return rideTripId;
        }

        public void setRideTripId(String rideTripId) {
            this.rideTripId = rideTripId;
        }

        public String getRideType() {
            return rideType;
        }

        public void setRideType(String rideType) {
            this.rideType = rideType;
        }

        public String getFutureDate() {
            return futureDate;
        }

        public void setFutureDate(String futureDate) {
            this.futureDate = futureDate;
        }

        public String getFutureTime() {
            return futureTime;
        }

        public void setFutureTime(String futureTime) {
            this.futureTime = futureTime;
        }

        public String getRideDriverId() {
            return rideDriverId;
        }

        public void setRideDriverId(String rideDriverId) {
            this.rideDriverId = rideDriverId;
        }

        public String getRideBookedOn() {
            return rideBookedOn;
        }

        public void setRideBookedOn(String rideBookedOn) {
            this.rideBookedOn = rideBookedOn;
        }

        public String getRideBookedOnDate() {
            return rideBookedOnDate;
        }

        public void setRideBookedOnDate(String rideBookedOnDate) {
            this.rideBookedOnDate = rideBookedOnDate;
        }

        public String getRideBookedOnTime() {
            return rideBookedOnTime;
        }

        public void setRideBookedOnTime(String rideBookedOnTime) {
            this.rideBookedOnTime = rideBookedOnTime;
        }

        public String getRideAmount() {
            return rideAmount;
        }

        public void setRideAmount(String rideAmount) {
            this.rideAmount = rideAmount;
        }

        public String getRideStatus() {
            return rideStatus;
        }

        public void setRideStatus(String rideStatus) {
            this.rideStatus = rideStatus;
        }

        public String getRidePaymentStatus() {
            return ridePaymentStatus;
        }

        public void setRidePaymentStatus(String ridePaymentStatus) {
            this.ridePaymentStatus = ridePaymentStatus;
        }

        public String getRidePaymentTime() {
            return ridePaymentTime;
        }

        public void setRidePaymentTime(String ridePaymentTime) {
            this.ridePaymentTime = ridePaymentTime;
        }

        public String getRideSeats() {
            return rideSeats;
        }

        public void setRideSeats(String rideSeats) {
            this.rideSeats = rideSeats;
        }

        public String getRideFrom() {
            return rideFrom;
        }

        public void setRideFrom(String rideFrom) {
            this.rideFrom = rideFrom;
        }

        public String getRideTo() {
            return rideTo;
        }

        public void setRideTo(String rideTo) {
            this.rideTo = rideTo;
        }

        public DriverDetails getDriverDetails() {
            return driverDetails;
        }

        public void setDriverDetails(DriverDetails driverDetails) {
            this.driverDetails = driverDetails;
        }

        public DriveDetais getDriveDetais() {
            return driveDetais;
        }

        public void setDriveDetais(DriveDetais driveDetais) {
            this.driveDetais = driveDetais;
        }

        public PolyLines getPolyLines() {
            return polyLines;
        }

        public void setPolyLines(PolyLines polyLines) {
            this.polyLines = polyLines;
        }

        public String getPolyLine() {
            return polyLine;
        }

        public void setPolyLine(String polyLine) {
            this.polyLine = polyLine;
        }

    }

    public class DriveDetais {

        @SerializedName("distance")
        @Expose
        private Object distance;
        @SerializedName("time")
        @Expose
        private Object time;

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

    }


    public class DriverDetails {

        @SerializedName("driver_id")
        @Expose
        private String driverId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("institution_id")
        @Expose
        private String institutionId;
        @SerializedName("institution_name")
        @Expose
        private String institutionName;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("phone_country")
        @Expose
        private String phoneCountry;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("vehicle_no")
        @Expose
        private String vehicleNo;
        @SerializedName("verified")
        @Expose
        private String verified;
        @SerializedName("u_device_id")
        @Expose
        private String uDeviceId;
        @SerializedName("car_model")
        @Expose
        private String carModel;
        @SerializedName("car_capacity")
        @Expose
        private String carCapacity;
        @SerializedName("license_no")
        @Expose
        private String licenseNo;
        @SerializedName("license_expiry")
        @Expose
        private String licenseExpiry;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("ratings")
        @Expose
        private double ratings;
        @SerializedName("wallet_balance")
        @Expose
        private String walletBalance;

        public String getCar_colour() {
            return car_colour;
        }

        public void setCar_colour(String car_colour) {
            this.car_colour = car_colour;
        }

        public String getCar_image() {
            return car_image;
        }

        public void setCar_image(String car_image) {
            this.car_image = car_image;
        }

        @SerializedName("car_colour")
        @Expose
        private String car_colour;

        @SerializedName("car_image")
        @Expose
        private String car_image;

        @SerializedName("image")
        @Expose
        private String image;

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionId(String institutionId) {
            this.institutionId = institutionId;
        }

        public String getInstitutionName() {
            return institutionName;
        }

        public void setInstitutionName(String institutionName) {
            this.institutionName = institutionName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhoneCountry() {
            return phoneCountry;
        }

        public void setPhoneCountry(String phoneCountry) {
            this.phoneCountry = phoneCountry;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getVehicleNo() {
            return vehicleNo;
        }

        public void setVehicleNo(String vehicleNo) {
            this.vehicleNo = vehicleNo;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getUDeviceId() {
            return uDeviceId;
        }

        public void setUDeviceId(String uDeviceId) {
            this.uDeviceId = uDeviceId;
        }

        public String getCarModel() {
            return carModel;
        }

        public void setCarModel(String carModel) {
            this.carModel = carModel;
        }

        public String getCarCapacity() {
            return carCapacity;
        }

        public void setCarCapacity(String carCapacity) {
            this.carCapacity = carCapacity;
        }

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getLicenseExpiry() {
            return licenseExpiry;
        }

        public void setLicenseExpiry(String licenseExpiry) {
            this.licenseExpiry = licenseExpiry;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public double getRatings() {
            return ratings;
        }

        public void setRatings(double ratings) {
            this.ratings = ratings;
        }

        public String getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(String walletBalance) {
            this.walletBalance = walletBalance;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public class End {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }

    public class PolyLines {

        @SerializedName("start")
        @Expose
        private Start start = null;
        @SerializedName("end")
        @Expose
        private End end = null;

        public Start getStart() {
            return start;
        }

        public void setStart(Start start) {
            this.start = start;
        }

        public End getEnd() {
            return end;
        }

        public void setEnd(End end) {
            this.end = end;
        }

    }

    public class Start {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

    }
}



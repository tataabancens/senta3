import { fromByteArray } from "base64-js";
import { FormikHelpers } from "formik";
import { paths } from "../../constants/constants";
import axios from "../../api/axios";
import { parseJwt } from "./Jwt";
import { ResponseDetails } from "../serviceUtils/typings";
import { UserModel } from "../../models";
import { buildErrorResponse, buildSuccessResponse } from "../serviceUtils/returnTypesFactory";

export class AuthenticationService {
    
    private readonly loginPath = `${paths.RESTAURANTS}/1`;
    private readonly jwtTokenOffset = 7;
    private readonly ACCEPT_HEADER = "application/vnd.sentate.user.v1+json";

    public async tryLogin<T>(username: string,
        password: string,
        props: FormikHelpers<T>) {
            const encoder = new TextEncoder();
            const base64Credentials = fromByteArray(encoder.encode(`${username}:${password}`));
    
            try {
                const response = await axios.get(this.loginPath,
                    {
                        headers: {
                            'Authorization': `Basic ${base64Credentials}`
                        }
                    }
                );
                const authorization: string | undefined = response?.headers.authorization;
                const refreshToken: string | undefined = response?.headers['refresh-token'];

                const parsedToken = parseJwt(authorization?.substring(this.jwtTokenOffset)!)
                const { authorities: roles, userId } = parsedToken;

                const { isOk, data: user, error } = await this.getUser(authorization!, userId);

                if (!isOk) { console.log(error) }

                const contentURL = user?.content as string;
    
                const content = undefined;

                return { user: username, roles, authorization, refreshToken, id: userId, contentURL, content };
            } catch (err: any) {
                this.loginErrorHandler(err, props);
                return null;
            }
    }

    public async getUser(authorization: string, userId: number): Promise<ResponseDetails<UserModel>> {
        try {
            const response = await axios.get<UserModel>(paths.USERS + '/' + userId, {headers: {"Authorization": authorization, "Accept": this.ACCEPT_HEADER}});
            return buildSuccessResponse(response.data);
        } catch (e) {
            return buildErrorResponse(e as Error);
        }
    }
    
    private loginErrorHandler<T>(err: any, props: FormikHelpers<T>) {
        if (!err?.response) {
            props.setFieldError("username", "No server response");
        } else if (err.response?.status === 400) {
            props.setFieldError("username", "Missing username or password");
        } else if (err.response?.status === 401) {
            props.setFieldError("username", "Invalid username/password. Please try again.");
            props.setFieldError("password", "Invalid username/password. Please try again.");
        } else {
            props.setFieldError("username", "Login failed");
        }
    }
}
import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import TextInput from "../components/TextInput";
import PasswordInput from "../components/PasswordInput";
import Button from "../components/Button";
import "../styles/ResetPassword.css";
import {
    validateEmail,
    validatePassword,
    passwordsMatch,
} from "../utils/validation";

export default function ResetPassword() {
    const [email, setEmail] = useState("");
    const [code, setCode] = useState("");
    const [pass, setPass] = useState("");
    const [confirm, setConfirm] = useState("");
    const navigate = useNavigate();

    const r = useMemo(() => validatePassword(pass), [pass]);
    const passValid = r.valid;
    const valid =
        validateEmail(email) &&
        code &&
        passValid &&
        passwordsMatch(pass, confirm);

    function onSubmit(e) {
        e.preventDefault();
        if (!valid) return;
    }

    return (
        <div className="card" role="region" aria-label="Alterar senha">
            <h1 className="title">Alterar senha</h1>
            <form onSubmit={onSubmit} noValidate>
                <TextInput
                    id="email"
                    label="E-mail"
                    type="email"
                    placeholder="voce@exemplo.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    error={email && !validateEmail(email) ? "E-mail inválido" : ""}
                />
                <TextInput
                    id="code"
                    label="Código"
                    placeholder="Insira o código"
                    value={code}
                    onChange={(e) => setCode(e.target.value)}
                />
                <PasswordInput
                    id="newPassword"
                    label="Nova senha"
                    placeholder="Crie uma senha"
                    value={pass}
                    onChange={(e) => setPass(e.target.value)}
                />
                <div className="checklist" aria-live="polite">
                    <div className={r.len ? "ok" : "bad"}>
                        Pelo menos 6 caracteres
                    </div>
                    <div className={r.up ? "ok" : "bad"}>1 letra maiúscula</div>
                    <div className={r.low ? "ok" : "bad"}>
                        1 letra minúscula
                    </div>
                    <div className={r.num ? "ok" : "bad"}>1 número</div>
                    <div className={r.sp ? "ok" : "bad"}>
                        1 caractere especial
                    </div>
                </div>
                <PasswordInput
                    id="confirmNewPassword"
                    label="Confirmar nova senha"
                    placeholder="Repita a nova senha"
                    value={confirm}
                    onChange={(e) => setConfirm(e.target.value)}
                    error={
                        confirm && !passwordsMatch(pass, confirm)
                            ? "As senhas não coincidem"
                            : ""
                    }
                />
                <div className="actions row">
                    <Button
                        type="button"
                        variant="secondary"
                        onClick={() => navigate("/login")}
                    >
                        Cancelar
                    </Button>
                    <Button type="submit" disabled={!valid}>
                        Alterar Senha
                    </Button>
                </div>
            </form>
        </div>
    );
}

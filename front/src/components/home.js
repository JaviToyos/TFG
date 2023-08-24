import {Card} from 'primereact/card';

export default function Home(props) {
    return (
        <div>
            <Card title={props.mensaje} style={{width: '50rem', marginBottom: '2em'}}>
                <p className="p-m-0">
                    Bienvenido a la pantalla principal de la aplicación. En esta aplicación podrás:
                </p>
                <ul>
                    <li>Registrarte</li>
                    <li>Iniciar sesión y cerrar sesión</li>
                    <li>Recuperar tu contraseña si la has olvidado</li>
                    <li>Visualizar tus datos personales</li>
                    <li>Editar tus datos personales</li>
                    <li>Conectar tus cuentas bancarias a la aplicación</li>
                    <li>Visualizar tus cuentas bancarias</li>
                    <li>Editar datos de tus cuentas bancarias</li>
                    <li>Eliminar los enlaces a tus cuentas bancarias</li>
                    <li>Visualizar los movimientos de tus cuentas bancarias</li>
                    <li>Recargar los movimientos para visualizar nuevos</li>
                    <li>Filtrar la visualización de los movimientos</li>
                    <li>Categorizar los movimientos</li>
                    <li>Añadir categorías</li>
                    <li>Editar categorías</li>
                    <li>Eliminar categorías</li>
                    <li>Añadir criterios que definen una categoría</li>
                    <li>Editar criterios</li>
                    <li>Eliminar criterios</li>
                </ul>
            </Card>
        </div>
    );
}

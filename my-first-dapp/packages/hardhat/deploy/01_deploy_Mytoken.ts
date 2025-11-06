import { HardhatRuntimeEnvironment } from "hardhat/types";
import { DeployFunction } from "hardhat-deploy/types";
import { Contract } from "ethers";

/**
 * Deploys a contract named "Mytoken" using the deployer account.
 *
 * @param hre HardhatRuntimeEnvironment object.
 */
const deployMyToken: DeployFunction = async function (hre: HardhatRuntimeEnvironment) {
  /*
    On localhost, the deployer account is the one that comes with Hardhat, which is already funded.

    When deploying to live networks (e.g `yarn deploy --network sepolia`), the deployer account
    should have sufficient balance to pay for the gas fees for contract creation.
  */
  const { deployer } = await hre.getNamedAccounts();
  const { deploy } = hre.deployments;

  await deploy("MyToken", {
    from: deployer,
    // Contract constructor arguments
    // 我们的 Mytoken (ERC20) 构造函数不需要参数，所以这里是空数组 []
    args: [],
    log: true,
    // autoMine: can be passed to the deploy function to make the deployment process faster on local networks by
    // automatically mining the contract deployment transaction. There is no effect on live networks.
    autoMine: true,
  });

  // (我们删除了原来 YourContract 的 getContract 和 console.log 部分，
  // 因为我们的 Mytoken 合约没有 greeting() 函数)
};

export default deployMyToken;

// Tags are useful if you have multiple deploy files and only want to run one of them.
// e.g. yarn deploy --tags Mytoken
// ERC20 + 名字缩写 + 组长学号 **
deployMyToken.tags = ["ERC20ZHR202330552361"];